package br.com.casadocodigo.loja.service;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import br.com.casadocodigo.loja.daos.CompraDao;
import br.com.casadocodigo.loja.models.Compra;

@Path("/pagamento")
public class PagamentoService  {
	
    @Inject
    private CompraDao compraDao;
    
    @Inject
    private PagamentoGateway pagamentoGateway;
    
    @Context
    private ServletContext context;
    
    @Inject
    private JMSContext jmsContext;
    
    @Resource(name="java:/jms/topics/CarrinhoComprasTopico")
    private Destination destination;
    
    private static ExecutorService executor = Executors.newFixedThreadPool(50);
    
    // @Suspended informa que ao servidor que a chamada do metodo é assincrona
	
	@POST 
	public void pagar(@Suspended  final AsyncResponse ar, @QueryParam("uuid") String uuid) {
		Compra compra = compraDao.buscaPorUuid(uuid);
	    String contextPath = context.getContextPath();
	    
	    JMSProducer producer = jmsContext.createProducer();
	    
		 executor.submit(() ->{
			 
			 try {
			 String resposta = pagamentoGateway.pagar(compra.getTotal());
			 System.out.println(resposta);
			 
			 
			 producer.send(destination, compra.getUuid()); // envio da msg
			 
			 URI responseUri = UriBuilder.fromPath("http://localhost:8080" + contextPath + "/index.xhtml").queryParam("msg", "Compra Realizada com Sucesso").build();
			 Response response = Response.seeOther(responseUri).build();
			 

			 ar.resume(response);
			 
			 } catch (Exception e) {
				 ar.resume(new WebApplicationException(e));
			 }
		 });

	}

}

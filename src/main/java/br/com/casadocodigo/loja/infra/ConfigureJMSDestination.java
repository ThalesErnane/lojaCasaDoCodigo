package br.com.casadocodigo.loja.infra;

import javax.ejb.Singleton;
import javax.jms.JMSDestinationDefinition;

@JMSDestinationDefinition(
	    name="java:/jms/topics/CarrinhoComprasTopico",
	    interfaceName="javax.jms.Topic" // tipo de destino 
	)
@Singleton
public class ConfigureJMSDestination {

}

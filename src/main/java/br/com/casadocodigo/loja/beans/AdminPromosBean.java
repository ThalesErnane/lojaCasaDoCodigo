package br.com.casadocodigo.loja.beans;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.com.casadocodigo.loja.models.Promo;
import br.com.casadocodigo.loja.websockets.PromosEndpoint;

@Model
public class AdminPromosBean {

	// casadocodigo/admin/form.xhtml
	
	private Promo promo = new Promo();
	
	@Inject
	private PromosEndpoint promos;

	public void enviar() { // metodo da tela 
		promos.send(promo);
		
	}

	public Promo getPromo() {
		return promo;
	}

	public void setPromo(Promo promo) {
		this.promo = promo;
	}

}

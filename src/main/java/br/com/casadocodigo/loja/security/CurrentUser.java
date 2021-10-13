package br.com.casadocodigo.loja.security;

import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.com.casadocodigo.loja.daos.SecurityDao;
import br.com.casadocodigo.loja.models.SystemUser;

@Model // vai durar apenas um unico request
public class CurrentUser {

	@Inject
	private HttpServletRequest request;

	@Inject
	private SecurityDao securityDao;

	private SystemUser systemUser;

	public SystemUser get() {
		return systemUser;
	}
	
	public String logout() {
		request.getSession().invalidate(); 
		return "/admin/livros/lista.xhtml?faces-redirect=true"; // vai pra tela de login  
	}
	
	public boolean hasRole(String role) {
		return request.isUserInRole(role);
	}
	
	@PostConstruct // carrega  usuário logo depois que a classe for instanciada
	public void loadSystemUser() {
		Principal principal = request.getUserPrincipal();
		
		if (principal != null) {
			String email = request.getUserPrincipal().getName();
			systemUser = securityDao.findByEmail(email);

		}
	}

}

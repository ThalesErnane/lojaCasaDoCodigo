package br.com.casadocodigo.loja.beans;

import java.io.IOException;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.Part;
import javax.transaction.Transactional;

import br.com.casadocodigo.loja.daos.AutorDao;
import br.com.casadocodigo.loja.daos.LivroDao;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Autor;
import br.com.casadocodigo.loja.models.Livro;

// CDI
@Model
public class AdminLivrosBean {

	private Livro livro = new Livro();

	@Inject
	private AutorDao autorDao;

	@Inject // Context and Dependency Injection
	private LivroDao dao;
	
	private Part capaLivro;

	@Inject
	private FacesContext context;

	public AdminLivrosBean() {
		 context = FacesContext.getCurrentInstance();
	}

	@Transactional
	public String salvar() throws IOException {
		dao.salvar(livro);
        FileSaver fileSaver = new FileSaver(); // Nossa nova classe
        livro.setCapaPath(fileSaver.write(capaLivro, "livros")); // Já chamamos o método write e já retornamos o path direto para o Livro
  
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Livro Cadastrado com sucesso"));

		return "/livros/lista?faces-redirect=true"; // E retornamos a página que o usuário irá sem o .xhtml
	}

	public List<Autor> getAutores() {
		return autorDao.listar();
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Part getCapaLivro() {
		return capaLivro;
	}

	public void setCapaLivro(Part capaLivro) {
		this.capaLivro = capaLivro;
	}

}

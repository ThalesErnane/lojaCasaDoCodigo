package br.com.casadocodigo.loja.beans;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.casadocodigo.loja.daos.LivroDao;
import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.CarrinhoItem;
import br.com.casadocodigo.loja.models.Livro;

@Named
@RequestScoped
public class CarrinhoComprasBean {

	@Inject
	private LivroDao livroDao;

	@Inject
	private CarrinhoCompras carrinho;

	public String add(Integer id) {
		Livro livro = livroDao.buscarPorId(id);
		CarrinhoItem item = new CarrinhoItem(livro);
		carrinho.add(item);

		return "carrinho?faces-redirect=true";

	}
	
	public List<CarrinhoItem> getItens(){
		return carrinho.getItens();
	}
	
	public void remover(CarrinhoItem item) {
	    carrinho.remover(item);
	}

}

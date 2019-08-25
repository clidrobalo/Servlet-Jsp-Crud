package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ProdutoBean;
import dao.DaoCategoria;
import dao.ProdutoDao;

/**
 * Servlet implementation class ProdutoServlet
 */
@WebServlet("/ProdutoServlet")
public class ProdutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	ProdutoDao produtoDao = new ProdutoDao();
	DaoCategoria daoCategoria =  new DaoCategoria();
	
    public ProdutoServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String acao = request.getParameter("acao");
		boolean produtoRegistadoNaBD = false;
		
		RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
		
		if(acao.equals("listarTodos")) {
			
		} else if(acao.equals("delete")) {
			String id = request.getParameter("id");
			produtoDao.delete(Long.valueOf(id));
			
		} else if(acao.equals("editar")) {
			String id = request.getParameter("id");
			ProdutoBean produto = produtoDao.consultar(Long.valueOf(id));
			request.setAttribute("produto", produto);
		}
		
		request.setAttribute("produtos", produtoDao.getProdutos());
		request.setAttribute("categorias", daoCategoria.listar());
		request.setAttribute("produtoRegistado", produtoRegistadoNaBD);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		int quantidade  = Integer.parseInt(request.getParameter("quantidade"));
		String strCategoriaId = request.getParameter("categoria");
		
		
		boolean produtoExisteNaBD = false;
		boolean produtoRegistadoNaBD = false;
		boolean produtoAtualizadoNaBD = false;
		boolean valorInvalido = false;
		boolean categoriaInvalido = false;
		
		/*
		 * A validação de não aceitar post de campos vazios fica por responsabilidade da view
		 */
		
		ProdutoBean produto = new ProdutoBean();
		produto.setNome(nome);
		produto.setQuantidade(quantidade);
		
		
		
		/*
		 * Caso o user não passar um numero inteiro
		 */
		Double valor = null;
		try {
			valor = Double.valueOf(request.getParameter("valor"));
		} catch (NumberFormatException e) {
			valorInvalido = true;
			RequestDispatcher view  = request.getRequestDispatcher("/cadastroProduto.jsp");
			request.setAttribute("produtos", produtoDao.getProdutos());
			request.setAttribute("categorias", daoCategoria.listar());
			request.setAttribute("produto", produto);
			request.setAttribute("valorInvalido", valorInvalido);
			view.forward(request, response);
		}
		
		
		produto.setValor(valor);
		
		//Verificar se o valor não eh default
		if(strCategoriaId != null) {
			produto.setCategoriaId(Long.parseLong(strCategoriaId));
		} else {
			categoriaInvalido = true;
			RequestDispatcher view  = request.getRequestDispatcher("/cadastroProduto.jsp");
			request.setAttribute("produtos", produtoDao.getProdutos());
			request.setAttribute("categorias", daoCategoria.listar());
			request.setAttribute("produto", produto);
			request.setAttribute("categoriaInvalido", categoriaInvalido);
			view.forward(request, response);
		}
		
		/*
		 * verificar se o produto ja existe, e o objetivo é editar
		 */
		
		if(id == null || id.isEmpty()) {
			/*
			 * Produto não existe!
			 */
			if(!produtoDao.produtoExiste(produto.getNome())) {
				//Salvar novo produto
				produtoDao.salvar(produto);
				produtoExisteNaBD = true;
				produtoRegistadoNaBD = true;
			} else {
				request.setAttribute("produto", produto);
				produtoExisteNaBD = true;
			}
			
		} else {
			/*
			 * Produto ja existe, Atualizar o produto!
			 */
			produto.setId(Long.valueOf(id));
			produtoDao.actualizar(produto);
			produtoAtualizadoNaBD = true;
		}
		
		RequestDispatcher view  = request.getRequestDispatcher("/cadastroProduto.jsp");
		request.setAttribute("produtos", produtoDao.getProdutos());
		request.setAttribute("categorias", daoCategoria.listar());
		request.setAttribute("produtoExiste", produtoExisteNaBD);
		request.setAttribute("produtoRegistado", produtoRegistadoNaBD);
		request.setAttribute("produtoAtualizado", produtoAtualizadoNaBD);
		view.forward(request, response);
	}

	public void redirect() {
		
	}
}

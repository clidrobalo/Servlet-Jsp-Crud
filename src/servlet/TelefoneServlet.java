package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ProdutoBean;
import beans.TelefoneBean;
import dao.ProdutoDao;
import dao.TelefoneDao;

/**
 * Servlet implementation class TelefoneServlet
 */
@WebServlet("/TelefoneServlet")
public class TelefoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	TelefoneDao telefoneDao = new TelefoneDao();
	
    public TelefoneServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String acao = request.getParameter("acao");
		String idTelefone = request.getParameter("idTelefone");
		String idUsuario = request.getParameter("idUsuario");
		String user = request.getParameter("user");
		boolean telefoneRegistadoNaBD = false;
		
		RequestDispatcher view = request.getRequestDispatcher("/cadastroTelefone.jsp");
		
		if(acao.equals("listarTodos")) {
			
		} else if(acao.equals("delete")) {
			telefoneDao.delete(Long.valueOf(idTelefone));
			
		} else if(acao.equals("editar")) {
			TelefoneBean telefone = telefoneDao.consultar(Long.valueOf(idTelefone));
			request.setAttribute("telefone", telefone);
		}
		request.setAttribute("idUsuario", idUsuario);
		request.setAttribute("user", user);
		request.setAttribute("telefones", telefoneDao.getTelefones(Long.valueOf(idUsuario)));
		request.setAttribute("telefoneRegistado", telefoneRegistadoNaBD);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String id = request.getParameter("id");
		Long idUsuario = Long.valueOf(request.getParameter("idUser"));
		String user = request.getParameter("usuario");
		int numero  = Integer.parseInt(request.getParameter("numero"));
		boolean telefoneExisteNaBD = false;
		boolean telefoneRegistadoNaBD = false;
		boolean telefoneAtualizadoNaBD = false;
		
		/*
		 * A validação de não aceitar post de campos vazios fica por responsabilidade da view
		 */
		
		TelefoneBean telefone = new TelefoneBean();
		telefone.setNumero(numero);
		telefone.setIdUsuario(idUsuario);
		
		/*
		 * verificar se o telefone ja existe, e o objetivo é editar
		 */
		
		if(id == null || id.isEmpty()) {
			/*
			 * Telefone não existe!
			 */
			if(!telefoneDao.telefoneExiste(numero)) {
				//Salvar novo telefone
				telefoneDao.salvar(telefone);
				telefoneExisteNaBD = true;
				telefoneRegistadoNaBD = true;
			} else {
				request.setAttribute("telefone", telefone);
				telefoneExisteNaBD = true;
			}
			
		} else {
			/*
			 * Telefone ja existe, Atualizar o telefone!
			 */
			telefone.setId(Long.valueOf(id));
			telefoneDao.actualizar(telefone);
			telefoneAtualizadoNaBD = true;
		}
		
		RequestDispatcher view  = request.getRequestDispatcher("/cadastroTelefone.jsp");
		request.setAttribute("telefones", telefoneDao.getTelefones(idUsuario));
		request.setAttribute("idUsuario", idUsuario);
		request.setAttribute("user", user);
		request.setAttribute("telefoneExiste", telefoneExisteNaBD);
		request.setAttribute("telefoneRegistado", telefoneRegistadoNaBD);
		request.setAttribute("telefoneAtualizado", telefoneAtualizadoNaBD);
		view.forward(request, response);
	}

}

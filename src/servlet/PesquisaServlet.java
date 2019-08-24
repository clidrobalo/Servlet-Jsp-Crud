package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoUsuario;

/**
 * Servlet implementation class PesquisaServlet
 */
@WebServlet("/servletPesquisa")
public class PesquisaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	DaoUsuario daoUsuario = new DaoUsuario();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String valorPesquisa = request.getParameter("valorPesquisa");
		
		if(!valorPesquisa.isEmpty()) {
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("usuarios", daoUsuario.getUsers(valorPesquisa));
			view.forward(request, response);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("usuarios", daoUsuario.getUsers());
			view.forward(request, response);
		}
	}

}

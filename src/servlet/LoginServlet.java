package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.UsuarioBean;
import dao.DaoLogin;
import dao.DaoUsuario;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DaoLogin daoLogin = new DaoLogin();
	
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("User: " + request.getParameter("user"));
		System.out.println("Senha: " + request.getParameter("senha"));
		//--
		String user = request.getParameter("user");
		String senha = request.getParameter("senha");
		boolean existeCridencias = false;
		//--
		//beanCursoJsp bean = new beanCursoJsp();
		//bean.setUser(user);
		//bean.setUser(senha);
		//--
		if(!(user.isEmpty() && senha.isEmpty())) {
			existeCridencias = true;
			request.setAttribute("existeCridencias", existeCridencias);
			try {
				if(daoLogin.validarLogin(user, senha)) {
					UsuarioBean usuario = daoLogin.getUser(user);
					RequestDispatcher dispacher = request.getRequestDispatcher("acessoLiberado.jsp");
					dispacher.forward(request, response);
				} else {
					RequestDispatcher dispacher = request.getRequestDispatcher("acessoNegado.jsp");
					dispacher.forward(request, response);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			request.setAttribute("existeCridencias", existeCridencias);
			RequestDispatcher dispacher = request.getRequestDispatcher("index.jsp");
			dispacher.forward(request, response);
		}
		
	}
}

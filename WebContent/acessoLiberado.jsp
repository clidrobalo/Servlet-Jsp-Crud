<jsp:useBean id="calcula" class="beans.UsuarioBean" type="beans.UsuarioBean" scope="page"></jsp:useBean>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bem vindo</title>
</head>
<body>	

	<div style="background-color: #26a65b; padding: 20px; color: #FFF; margin-bottom: 10px;">
		<a href="acessoLiberado.jsp">
			<div style="display: inline; ">
				<img src="resources/img/home.png" alt="Home">
			</div>
		</a>
		<a href="index.jsp">
			<div style="display: inline; margin-left: 1px;">
				<img src="resources/img/exit.png" alt="Exit">
			</div>
		</a>
		
		<center><h1>Bem vindo ao sistema em jsp</h1></center>
	</div>
	
	<center style="padding: 100px;">
		<table>
			<tr>
				<td><a href="salvarUsuario?acao=listarTodos"><img alt="Novo registo" src="resources/img/add-user.jpg" width="150px";></a></td>
				<td><a href="ProdutoServlet?acao=listarTodos"><img alt="Novo produto" src="resources/img/add-button.png" width="150px";></a></td>
			</tr>
			<tr style="text-align: center;">
				<td>Cadastro Usuario</td>
				<td>Cadastro Produto</td>
			</tr>
		
		</table>
	</center>
	
</body>
</html>
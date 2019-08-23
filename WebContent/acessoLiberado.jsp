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

	<div style="background-color: #26a65b; text-align: center; padding: 20px; color: #FFF; margin-bottom: 10px;">
		<h1>Bem vindo ao sistema em jsp</h1>
	</div>
	
	<a href="salvarUsuario?acao=listarTodos&tipoAcesso=${tipoAcesso}">
		<img alt="Novo registo" src="resources/img/add-user.jpg" width="10%";>
	</a>
	
	<a href="ProdutoServlet?acao=listarTodos">
		<img alt="Novo produto" src="resources/img/add-button.png" width="10%";>
	</a>
	
</body>
</html>
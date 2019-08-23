
<jsp:useBean id="calcula" class="beans.beanCursoJsp"
	type="beans.beanCursoJsp" scope="page"></jsp:useBean>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myprefix" uri="WEB-INF/testeTag.tld"%>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="resources/css/estilo.css">
	<meta charset="ISO-8859-1">
	<title>Login</title>
</head>
<body>

	<div class="login-page">
		<div class="form">
			<c:if test="${!existeCridencias}">
				<h4 style="color: tomato; text-align: center;">User e Senha vazio!</h4>
			</c:if>
			<form class="login-form" action="LoginServlet" method="post">
				<input type="text" name="user" placeholder="username" required/>
				<input type="password" name="senha" placeholder="password" required/>
				<button>login</button>
			</form>
		</div>
	</div>
</body>
</html>
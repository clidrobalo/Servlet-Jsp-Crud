<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Nome recebido</title>
</head>
<body>
	<h3>
	<%= "Página para receber nome por parametro" %>
	</h3>
	Curso: <%= session.getAttribute("curso")%>
	<hr>
	<% 
		String nome = "Nome recebido: ";
		out.print(nome + request.getParameter("nome"));
	%>
</body>
</html>
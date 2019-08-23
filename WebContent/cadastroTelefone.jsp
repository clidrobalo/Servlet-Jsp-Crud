<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link rel="stylesheet" href="resources/css/estilo.css">
<script type="text/javascript" src="resources/js/script.js"></script>
<meta charset="ISO-8859-1">
<title>Registo telefone</title>
</head>
<body>

	<a href="acessoLiberado.jsp">
		<div class="back-link" style="padding: 20px; background-color: #FFF; display: inline;">
			Inicio
		</div>
	</a>
	<a href="index.jsp">
		<div class="back-link" style="padding: 20px; background-color: #FFF; display: inline; margin-left: 1px;">
			Sair
		</div>
	</a>
	<div class="login-page">
		<p class="titulo">Novo registo</p>
		<div class="form">
			<c:if test="${telefoneExiste && !telefoneRegistado}">
				<p style="color: tomato;">Telefone ja existe!</p>
			</c:if>

			<c:if test="${telefoneInvalido}">
				<p style="color: tomato;">Numero invalido!</p>
			</c:if>

			<c:if test="${telefoneRegistado}">
				<p style="color: green;">Telefone registado com sucesso!</p>
			</c:if>

			<c:if test="${telefoneAtualizado}">
				<p style="color: green;">Telefone atualizado com sucesso!</p>
			</c:if>

			<form class="login-form" action="TelefoneServlet" method="post"
				onsubmit="validarCampos()">
				<input type="hidden" name="id" value="${telefone.id }" required>
				<input type="number" name="idUser" value="${idUsuario }"
					placeholder="Id Usuario"  required/> 
					<input type="text" name="usuario" value="${user }"
					placeholder="Usuario" required/> <input type="number"
					name="numero" id="numero" value="${telefone.numero }"
					placeholder="Numero" required />
				<button type="">Salvar</button>
			</form>
			<c:if test="${telefone != null }">
				<a href="TelefoneServlet?acao=listarTodos&idTelefone=${telefone.id}&idUsuario=${idUsuario}&user=${user}">
					<button style="background-color: tomato;">Cancelar</button>
			</c:if>
		</div>
	</div>

	<hr>

	<table class="table" style="width: 60%; margin: auto;">
		<thead class="thead-dark">
			<tr style="text-align: center;">
				<th scope="col">Id</th>
				<th scope="col">Id Usuario</th>
				<th scope="col">Numero</th>
				<th scope="col">Excluir</th>
				<th scope="col">Editar</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="telefone" items="${telefones}">
				<tr scope="row" style="text-align: center;">
					<td><c:out value="${telefone.id}"></c:out></td>
					<td><c:out value="${telefone.idUsuario}"></c:out></td>
					<td><c:out value="${telefone.numero}"></c:out></td>
					<td><a href="TelefoneServlet?acao=delete&idTelefone=${telefone.id}&idUsuario=${idUsuario}&user=${user}"><img
							alt="Excluir" src="resources/img/delete-icon.png" width="30px"></a>
					<td><a href="TelefoneServlet?acao=editar&idTelefone=${telefone.id}&idUsuario=${idUsuario}&user=${user}"><img
							alt="Editar" src="resources/img/edit-icon.png" width="30px"></a>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
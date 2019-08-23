<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js" type="text/javascript"></script>
	<script src="resources/js/jquery.maskMoney.js" type="text/javascript"></script>
	<meta charset="ISO-8859-1">
	<title>Registo produto</title>
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
			<c:if test="${produtoExiste && !produtoRegistado}">
				<p style="color: tomato;">Produto ja existe!</p>
			</c:if>

			<c:if test="${valorInvalido}">
				<p style="color: tomato;">Valor invalido!</p>
			</c:if>

			<c:if test="${produtoRegistado}">
				<p style="color: green;">Produto registado com sucesso!</p>
			</c:if>

			<c:if test="${produtoAtualizado}">
				<p style="color: green;">Produto atualizado com sucesso!</p>
			</c:if>

			<form class="login-form" action="ProdutoServlet" method="post"
				onsubmit="validarCampos()">
				<input type="hidden" name="id" value="${produto.id }" required>
				<input type="text" name="nome" value="${produto.nome }"
					placeholder="Nome" required /> <input type="number"
					name="quantidade" id="quantidade" value="${produto.quantidade }"
					placeholder="Quantidade" required />
					<input type="number step="any" name="valor" id="valor" value="${produto.valor }" placeholder="Valor-$" required />
				<button type="">Salvar</button>
			</form>
			<c:if test="${produto != null }">
				<a href="ProdutoServlet?acao=listarTodos&id=${produto.id}">
					<button style="background-color: tomato;">Cancelar</button>
			</c:if>
		</div>
	</div>

	<hr>

	<table class="table" style="width: 60%; margin: auto;">
		<thead class="thead-dark">
			<tr style="text-align: center;">
				<th scope="col">Id</th>
				<th scope="col">Nome</th>
				<th scope="col">Quantidade</th>
				<th scope="col">Valor</th>
				<th scope="col">Excluir</th>
				<th scope="col">Editar</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="produto" items="${produtos}">
				<tr scope="row" style="text-align: center;">
					<td><c:out value="${produto.id}"></c:out></td>
					<td><c:out value="${produto.nome}"></c:out></td>
					<td><c:out value="${produto.quantidade}"></c:out></td>
					<td>
						<fmt:formatNumber value = "${produto.valor}" type = "number" pattern="###.### $"/>
					</td>
					<td><a href="ProdutoServlet?acao=delete&id=${produto.id}"><img
							alt="Excluir" src="resources/img/delete-icon.png" width="30px"></a>
					<td><a href="ProdutoServlet?acao=editar&id=${produto.id}"><img
							alt="Editar" src="resources/img/edit-icon.png" width="30px"></a>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>

</html>
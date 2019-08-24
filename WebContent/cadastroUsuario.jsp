<%@page import="beans.UsuarioBean"%>
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
	<title>Registo</title>
	
	<!-- Adicionando JQuery -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous">
    </script>

</head>
<body>

	<a href="acessoLiberado.jsp">
		<div class="back-link" style="padding: 20px; background-color: #FFF; display: inline;">
			<img src="resources/img/home.png" alt="Home">
		</div>
	</a>
	<a href="index.jsp">
		<div class="back-link" style="padding: 20px; background-color: #FFF; display: inline; margin-left: 1px;">
			<img src="resources/img/exit.png" alt="Exit">
		</div>
	</a>
	<div class="login-page">
		<p class="titulo">Novo registo</p>
		<div class="form">
			<c:if test="${userExiste == 2}">
				<p style="color: tomato;">Usuário ja existe. Tente um novo!</p>
			</c:if>
			<c:if test="${userExiste == 1}">
				<p style="color: green;">Usuário registado com sucesso!</p>
			</c:if>
			
			<form class="login-form" action="salvarUsuario" method="post" enctype="multipart/form-data">
				<table>
					<input type="hidden" name="id" value="${user.id }">
					<tr>
						<td colspan="2" style="background-color: gray; padding: 8px; color: #FFF; font-weight: bold;">
							Utilizador
							<select name="tipoUtilizador">
							 <option value="">Selecione um perfil</option>	
							  <option value="admin" ${user.tipoAcesso eq 'Admin' ? 'selected=\"selected\"' : ''}>Admin</option>	
							  <option value="convidado" ${user.tipoAcesso eq 'Convidado' ? 'selected=\"selected\"' : ''}>Convidado</option>
							</select>
	 					</td>
					</tr>
					<tr> 
						<td><input type="text" id="login" name="login" value="${user.user }" placeholder="username" required /></td>
						<td><input type="number" id="cep" name="cep" value="${user.cep }" placeholder="Cep" onblur="consultaCep()" /></td>
					</tr>
					<tr>
						<td><input type="text" id="nome" name="nome" value="${user.nome }" placeholder="name" required /> </td>
						<td><input type="number" id="telefone" name="telefone" value="${user.telefone }" placeholder="telefone" /> </td>
					</tr>
					<tr>
						<td><input type="text" id="rua" name="rua" value="${user.rua }" placeholder="Rua" /> </td>
						<td><input type="text" id="bairro" name="bairro" value="${user.bairro }" placeholder="Bairro" /> </td>
					</tr>
					<tr>
						<td><input type="text" id="cidade" name="cidade" value="${user.cidade }" placeholder="Cidade"  /> </td>
						<td><input type="text" id="uf" name="uf" value="${user.uf }" placeholder="UF"/> </td>
					</tr>
					<tr>
						<td><input type="text" id="ebge" name="ibge" value="${user.ibge }" placeholder="EBGE"/> </td>
						<td><input type="password" id="telefone" name="senha" value="${user.senha }" placeholder="password" required /></td>
					</tr>
					<tr>
						<td colspan="2">Foto</td>
					</tr>
					<tr>
						<td colspan="2"><input type="file" name="foto" value="${user.fotoBase64 }"/>
						<input type="hidden" name="fotoHidden" value="${user.fotoBase64 }"/></td>
						<input type="hidden" name="contentTypeHidden" value="${user.contentType }"/></td>
					</tr>
					<tr>
						<td colspan="2">Curriculo</td>
					</tr>
					<tr>
						<td colspan="2"><input type="file" name="curriculo" value="${user.curriculo }"/></td>
						<input type="hidden" name="curriculoHidden" value="${user.curriculo }"/></td>
						<input type="hidden" name="contentTypeCurriculoHidden" value="${user.contentTypeCurriculo }"/></td>
					</tr>
					<tr  style="background-color: gray; color: #FFF;">
						<td>Ativo: </td>
						<td><input type="checkbox" id="ativo" name="ativo"
								
								<%
									if(request.getAttribute("user") != null) {
										UsuarioBean user = (UsuarioBean) request.getAttribute("user");
										if(user.isAtivo()) {
											out.print(" ");
											out.print("checked=\"checked\"");
											out.print(" ");
										}
									}
								
								%>					
							>
						</td>
					</tr>
					<tr>
						<td>Sexo: </td>
						<td>
							<table>
								<tr>
									<td><input type="radio" name="sexo" value="masculino" ${user.sexo eq 'masculino' ? 'checked' : ''}>------</td>
									<td>Masculino</td>
								</tr>
								<tr>
									<td><input type="radio" name="sexo" value="feminino" ${user.sexo eq 'feminino' ? 'checked' : ''}></td>
									<td>Feminino</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<button type="submit" style="margin-top: 2px;">Salvar</button>
			</form>
			<c:if test="${user != null }">
				<a href="salvarUsuario?acao=listarTodos&id=${user.id}&tipoAcesso=${tipoAcesso}">
					<button style="background-color: tomato;">Cancelar</button>
				</a>
			</c:if>
		</div>
	</div>

	<center><h3><strong>Lista de Usuarios</strong></h3></center>
	<form method="post" action="servletPesquisa" style="margin-left: 20%">
		<input type="text" name="valorPesquisa" placeholder="Pesquisar usuario..." style="width: 50%">
		<input type="submit" value="Pesquisar">
	</form>
	<hr> 

	<table class="table" style="width: 60%; margin: auto;">
		<thead class="thead-dark">
			<tr style="text-align: center;">
				<th scope="col">Id</th>
				<th scope="col">User</th>
				<th scope="col">Nome</th>
				<th scope="col">Foto</th>
				<th scope="col">Curriculo</th>
				<th scope="col">Telefone</th>
				<th scope="col">Excluir</th>
				<th scope="col">Editar</th>
				<th scope="col">Telefones</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${usuarios}">
				<tr scope="row" style="text-align: center;">
					<td><c:out value="${user.id}"></c:out></td>
					<td><c:out value="${user.user}"></c:out></td>
					<td><c:out value="${user.nome}"></c:out></td>
					<td>
						<c:if test="${user.hasFoto()}">
							<a href="salvarUsuario?acao=download&tipo=imagem&id=${user.id}"><img alt="Foto" src='<c:out value="${user.miniaturaBase64}"></c:out>' width="32px"></a>
						</c:if>
						<c:if test="${!user.hasFoto() }">
							Sem Foto
						</c:if>
					</td>
					<td>
						<c:if test="${!user.curriculo.isEmpty() }">
							<a href="salvarUsuario?acao=download&tipo=curriculo&id=${user.id}">Curriculo</a>
						</c:if>
						<c:if test="${user.curriculo.isEmpty() }">
							Sem Curriculo
						</c:if>
					</td>
					<td><c:out value="${user.telefone}"></c:out></td>
					<td><a href="salvarUsuario?acao=delete&id=${user.id}&tipoAcesso=${tipoAcesso}"><img
							alt="Excluir" src="resources/img/delete-icon.png" width="30px" onclick="return confirm('Excluir registo?');"></a>
					<td><a href="salvarUsuario?acao=editar&id=${user.id}&tipoAcesso=${tipoAcesso}"><img
							alt="Editar" src="resources/img/edit-icon.png" width="30px"></a>
					<td><a href="TelefoneServlet?acao=verTelefones&idUsuario=${user.id}&user=${user.user}"><img
							alt="Telefones" src="resources/img/tel-icon.png" width="30px"></a>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
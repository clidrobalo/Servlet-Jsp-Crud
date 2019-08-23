package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.UsuarioBean;
import connection.SingleConnection;

public class DaoLogin {

	private Connection connection;
	
	public DaoLogin() {
		connection = SingleConnection.getConnection();
	}
	
	public boolean validarLogin(String user, String senha) throws Exception{
		
		String query = "SELECT * FROM usuario WHERE login = ? and senha = ?";
		
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, user);
		statement.setString(2, senha);
		ResultSet resultSet = statement.executeQuery();
		
		//caso true, existe usuario
		return resultSet.next();
	}
	
public UsuarioBean getUser(String userParam) throws Exception{
		
		String query = "SELECT * FROM usuario WHERE login = ?";
		
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, userParam);
		
		UsuarioBean user = new UsuarioBean();
		try {
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				user.setId(Long.parseLong(result.getString("id")));
				user.setUser(result.getString("login"));
				user.setNome(result.getString("nome"));
				user.setTelefone(result.getString("telefone"));
				user.setCep(result.getString("cep"));
				user.setRua(result.getString("rua"));
				user.setBairro(result.getString("bairro"));
				user.setCidade(result.getString("cidade"));
				user.setUf(result.getString("uf"));
				user.setIbge(result.getString("ibge"));
				user.setSenha(result.getString("senha"));
				user.setFotoBase64(result.getString("fotobase64"));
				user.setContentType(result.getString("contenttype"));
				user.setCurriculo(result.getString("curriculo"));
				user.setContentTypeCurriculo(result.getString("contenttypecurriculo"));
				user.setMiniaturaBase64(result.getString("miniaturabase64"));
				user.setTipoAcesso(result.getString("tipoacesso"));
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
}

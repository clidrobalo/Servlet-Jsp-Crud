package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}

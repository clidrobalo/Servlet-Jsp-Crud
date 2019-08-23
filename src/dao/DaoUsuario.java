package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.beanCursoJsp;
import connection.SingleConnection;


public class DaoUsuario {

private Connection connection;
	
	public DaoUsuario() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvar(beanCursoJsp user)  {
		String query = "INSERT INTO usuario(login, nome, telefone, cep, rua, bairro, cidade, uf, ibge, senha, fotobase64, contenttype, curriculo, contenttypecurriculo, miniaturabase64) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, user.getUser());
			statement.setString(2, user.getNome());
			statement.setString(3, user.getTelefone());
			statement.setString(4, user.getCep());
			statement.setString(5, user.getRua());
			statement.setString(6, user.getBairro());
			statement.setString(7, user.getCidade());
			statement.setString(8, user.getUf());
			statement.setString(9, user.getIbge());
			statement.setString(10, user.getSenha());
			statement.setString(11, user.getFotoBase64());
			statement.setString(12, user.getContentType());
			statement.setString(13, user.getCurriculo());
			statement.setString(14, user.getContentTypeCurriculo());
			statement.setString(15, user.getMiniaturaBase64());
			statement.execute();
			// Salvar no banco de dados
			connection.commit();
		} catch (SQLException e) {
			// reverter o estado da base de dados
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}
	
	public List<beanCursoJsp> getUsers() {
		List<beanCursoJsp> lista = new ArrayList<beanCursoJsp>();

		String query = "SELECT * FROM usuario";

		try {
			PreparedStatement select = connection.prepareStatement(query);
			ResultSet resultado = select.executeQuery();

			while (resultado.next()) {
				beanCursoJsp bean = new beanCursoJsp();
				bean.setId(Long.parseLong(resultado.getString("id")));
				bean.setUser(resultado.getString("login"));
				bean.setNome(resultado.getString("nome"));
				bean.setTelefone(resultado.getString("telefone"));
				bean.setCep(resultado.getString("cep"));
				bean.setRua(resultado.getString("rua"));
				bean.setBairro(resultado.getString("bairro"));
				bean.setCidade(resultado.getString("cidade"));
				bean.setUf(resultado.getString("uf"));
				bean.setIbge(resultado.getString("ibge"));
				bean.setSenha(resultado.getString("senha"));
				//bean.setFotoBase64(resultado.getString("fotobase64"));
				bean.setContentType(resultado.getString("contenttype"));
				bean.setCurriculo(resultado.getString("curriculo"));
				bean.setContentTypeCurriculo("contenttypecurriculo");
				bean.setMiniaturaBase64("miniaturabase64");
				
				lista.add(bean);
			}

			return lista;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lista;
	}
	
	public void delete(Long id) {
		String query = "DELETE FROM usuario WHERE id = ?"; 
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			statement.execute();
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	public beanCursoJsp consultar(Long id) {
		String query = "SELECT * FROM usuario WHERE id = ?";
		
		beanCursoJsp bean = new beanCursoJsp();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				bean.setId(Long.parseLong(result.getString("id")));
				bean.setUser(result.getString("login"));
				bean.setNome(result.getString("nome"));
				bean.setTelefone(result.getString("telefone"));
				bean.setCep(result.getString("cep"));
				bean.setRua(result.getString("rua"));
				bean.setBairro(result.getString("bairro"));
				bean.setCidade(result.getString("cidade"));
				bean.setUf(result.getString("uf"));
				bean.setIbge(result.getString("ibge"));
				bean.setSenha(result.getString("senha"));
				bean.setFotoBase64(result.getString("fotobase64"));
				bean.setContentType(result.getString("contenttype"));
				bean.setCurriculo(result.getString("curriculo"));
				bean.setContentTypeCurriculo(result.getString("contenttypecurriculo"));
				bean.setMiniaturaBase64(result.getString("miniaturabase64"));
				return bean;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}
	
	public boolean userExiste(String login) {
		String query = "SELECT * FROM usuario WHERE login = ?";
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, login);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void actualizar(beanCursoJsp bean) {
		System.out.println("Atualizar");
		String query = "UPDATE usuario SET login = ?, nome = ?, telefone = ?, cep = ?, rua = ?, bairro = ?, cidade = ?, uf = ?, ibge = ?, senha = ?, fotobase64 = ?, contenttype = ?, curriculo = ?, contenttypecurriculo = ?, miniaturabase64 = ? WHERE id = ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bean.getUser());
			preparedStatement.setString(2, bean.getNome());
			preparedStatement.setString(3, bean.getTelefone());
			preparedStatement.setString(4, bean.getCep());
			preparedStatement.setString(5, bean.getRua());
			preparedStatement.setString(6, bean.getBairro());
			preparedStatement.setString(7, bean.getCidade());
			preparedStatement.setString(8, bean.getUf());
			preparedStatement.setString(9, bean.getIbge());
			preparedStatement.setString(10, bean.getSenha());
			preparedStatement.setString(11, bean.getFotoBase64());
			preparedStatement.setString(12, bean.getContentType());
			preparedStatement.setString(13, bean.getCurriculo());
			preparedStatement.setString(14, bean.getContentTypeCurriculo());
			preparedStatement.setString(15, bean.getMiniaturaBase64());
			preparedStatement.setLong(16, bean.getId());
			
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

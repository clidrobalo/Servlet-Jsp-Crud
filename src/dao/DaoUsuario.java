package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.UsuarioBean;
import connection.SingleConnection;


public class DaoUsuario {

private Connection connection;
	
	public DaoUsuario() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvar(UsuarioBean user)  {
		String query = "INSERT INTO usuario(login, nome, telefone, cep, rua, bairro, cidade, uf, ibge, senha, fotobase64, contenttype, curriculo, contenttypecurriculo, miniaturabase64, tipoacesso, ativo) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		
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
			statement.setString(16, user.getTipoAcesso());
			statement.setBoolean(17, user.isAtivo());
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
	
	public List<UsuarioBean> getUsers(String tipoAcesso) {
		List<UsuarioBean> lista = new ArrayList<UsuarioBean>();

		String query;
		
		if(tipoAcesso.equals("Admin")) {
			query = "SELECT * FROM usuario";
		} else {
			query = "SELECT * FROM usuario where tipoacesso <> ?";
		}

		try {
			PreparedStatement select = connection.prepareStatement(query);
			if(!tipoAcesso.equals("Admin")) {
				select.setString(1, "Admin");
			}
			
			ResultSet resultado = select.executeQuery();

			while (resultado.next()) {
				UsuarioBean user = new UsuarioBean();
				user.setId(Long.parseLong(resultado.getString("id")));
				user.setUser(resultado.getString("login"));
				user.setNome(resultado.getString("nome"));
				user.setTelefone(resultado.getString("telefone"));
				user.setCep(resultado.getString("cep"));
				user.setRua(resultado.getString("rua"));
				user.setBairro(resultado.getString("bairro"));
				user.setCidade(resultado.getString("cidade"));
				user.setUf(resultado.getString("uf"));
				user.setIbge(resultado.getString("ibge"));
				user.setSenha(resultado.getString("senha"));
				//bean.setFotoBase64(resultado.getString("fotobase64"));
				user.setContentType(resultado.getString("contenttype"));
				user.setCurriculo(resultado.getString("curriculo"));
				user.setContentTypeCurriculo(resultado.getString("contenttypecurriculo"));
				user.setMiniaturaBase64(resultado.getString("miniaturabase64"));
				user.setTipoAcesso(resultado.getString("tipoacesso"));
				user.setAtivo(resultado.getBoolean("ativo"));
				
				lista.add(user);
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
	
	public UsuarioBean consultar(Long id) {
		String query = "SELECT * FROM usuario WHERE id = ?";
		
		UsuarioBean bean = new UsuarioBean();
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
				bean.setTipoAcesso(result.getString("tipoacesso"));
				bean.setAtivo(result.getBoolean("ativo"));
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
	
	public void actualizar(UsuarioBean user) {
		System.out.println("Atualizar");
		String query = "UPDATE usuario SET login = ?, nome = ?, telefone = ?, cep = ?, rua = ?, bairro = ?, cidade = ?, uf = ?, ibge = ?, senha = ?, fotobase64 = ?, contenttype = ?, curriculo = ?, contenttypecurriculo = ?, miniaturabase64 = ?, tipoacesso = ?, ativo = ? WHERE id = ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user.getUser());
			preparedStatement.setString(2, user.getNome());
			preparedStatement.setString(3, user.getTelefone());
			preparedStatement.setString(4, user.getCep());
			preparedStatement.setString(5, user.getRua());
			preparedStatement.setString(6, user.getBairro());
			preparedStatement.setString(7, user.getCidade());
			preparedStatement.setString(8, user.getUf());
			preparedStatement.setString(9, user.getIbge());
			preparedStatement.setString(10, user.getSenha());
			preparedStatement.setString(11, user.getFotoBase64());
			preparedStatement.setString(12, user.getContentType());
			preparedStatement.setString(13, user.getCurriculo());
			preparedStatement.setString(14, user.getContentTypeCurriculo());
			preparedStatement.setString(15, user.getMiniaturaBase64());
			preparedStatement.setString(16, user.getTipoAcesso());
			preparedStatement.setBoolean(17, user.isAtivo());
			preparedStatement.setLong(18, user.getId());
			
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

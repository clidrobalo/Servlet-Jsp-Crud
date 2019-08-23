package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.TelefoneBean;
import connection.SingleConnection;

public class TelefoneDao {

	private Connection connection;
	
	public TelefoneDao() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvar(TelefoneBean telefone) {
		String query = "INSERT INTO telefone(idusuario, numero) VALUES(?, ?)";
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setLong(1, telefone.getIdUsuario());
			statement.setInt(2, telefone.getNumero());
			statement.execute();
			//salvar no banco de dados
			connection.commit();
		} catch (SQLException e) {
			//reverter caso de erro
			try {
				connection.rollback();
			} catch (Exception e1) {
				// TODO: handle exception
				e1.printStackTrace();
			}
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public boolean telefoneExiste(int numero) {
		String query = "SELECT * FROM telefone WHERE numero = ?";
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, numero);
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
	
	public List<TelefoneBean> getTelefones(Long idUsuario) {
		List<TelefoneBean> lista = new ArrayList<TelefoneBean>();

		String query = "SELECT * FROM telefone WHERE idusuario = ?";

		try {
			PreparedStatement select = connection.prepareStatement(query);
			select.setLong(1, idUsuario);
			ResultSet resultado = select.executeQuery();

			while (resultado.next()) {
				TelefoneBean telefone = new TelefoneBean();
				telefone.setId(Long.parseLong(resultado.getString("id")));
				telefone.setIdUsuario(resultado.getLong("idUsuario"));
				telefone.setNumero(resultado.getInt("numero"));
				
				lista.add(telefone);
			}

			return lista;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lista;
	}
	
	public void delete(Long id) {
		String query = "DELETE FROM telefone WHERE id = ?"; 
		
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
	
	public void actualizar(TelefoneBean telefone ) {
		System.out.println("Atualizar");
		String query = "UPDATE telefone SET idUsuario = ?, numero = ? WHERE id = ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, telefone.getIdUsuario());
			preparedStatement.setInt(2, telefone.getNumero());
			preparedStatement.setLong(3, telefone.getId());
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
	
	public TelefoneBean consultar(Long id) {
		String query = "SELECT * FROM telefone WHERE id = ?";
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				TelefoneBean telefone = new TelefoneBean();
				telefone.setId(result.getLong("id"));
				telefone.setIdUsuario(result.getLong("idUsuario"));
				telefone.setNumero(result.getInt("numero"));
				return telefone;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

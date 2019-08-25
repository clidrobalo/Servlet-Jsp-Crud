package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.ProdutoBean;
import beans.UsuarioBean;
import connection.SingleConnection;

public class ProdutoDao {

	private Connection connection;
	
	public ProdutoDao() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvar(ProdutoBean produto) {
		String query = "INSERT INTO produto(nome, quantidade, valor, categoria_id) VALUES(?, ?, ?, ?)";
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, produto.getNome());
			statement.setInt(2, produto.getQuantidade());
			statement.setDouble(3, produto.getValor());
			statement.setLong(4, produto.getCategoriaId());
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
	
	public boolean produtoExiste(String nome) {
		String query = "SELECT * FROM produto WHERE nome = ?";
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, nome);
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
	
	public List<ProdutoBean> getProdutos() {
		List<ProdutoBean> lista = new ArrayList<ProdutoBean>();

		String query = "SELECT * FROM produto";

		try {
			PreparedStatement select = connection.prepareStatement(query);
			ResultSet resultado = select.executeQuery();

			while (resultado.next()) {
				ProdutoBean produto = new ProdutoBean();
				produto.setId(Long.parseLong(resultado.getString("id")));
				produto.setNome(resultado.getString("nome"));
				produto.setQuantidade(Integer.parseInt(resultado.getString("quantidade")));
				produto.setValor(Double.valueOf(resultado.getString("valor")));
				produto.setCategoriaId(resultado.getLong("categoria_id"));
				lista.add(produto);
			}

			return lista;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lista;
	}
	
	public void delete(Long id) {
		String query = "DELETE FROM produto WHERE id = ?"; 
		
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
	
	public void actualizar(ProdutoBean produto ) {
		System.out.println("Atualizar");
		String query = "UPDATE produto SET nome = ?, quantidade = ?, valor = ?, categoria_id = ? WHERE id = ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, produto.getNome());
			preparedStatement.setInt(2, produto.getQuantidade());
			preparedStatement.setDouble(3, produto.getValor());
			preparedStatement.setLong(4, produto.getCategoriaId());
			preparedStatement.setLong(5, produto.getId());
			
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
	
	public ProdutoBean consultar(Long id) {
		String query = "SELECT * FROM produto WHERE id = ?";
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				ProdutoBean produto = new ProdutoBean();
				produto.setId(result.getLong("id"));
				produto.setNome(result.getString("nome"));
				produto.setQuantidade(result.getInt("quantidade"));
				produto.setValor(result.getDouble("valor"));
				produto.setCategoriaId(result.getLong("categoria_id"));
				return produto;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

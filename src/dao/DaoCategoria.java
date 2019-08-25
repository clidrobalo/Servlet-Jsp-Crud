package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.CategoriaBean;
import connection.SingleConnection;

public class DaoCategoria {
	
	Connection connection = SingleConnection.getConnection();
	
	public List<CategoriaBean> listar() {
		List<CategoriaBean> listaCategorias = new ArrayList<>();
		
		String query = "select * from  categoria";
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next()) {
				CategoriaBean categoria = new CategoriaBean();
				
				categoria.setId(resultado.getLong("id"));
				categoria.setNome(resultado.getString("nome"));
				
				listaCategorias.add(categoria);
			}
			
			return listaCategorias;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listaCategorias;
	}

}

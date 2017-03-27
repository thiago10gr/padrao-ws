package br.com.thiago10gr.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import br.com.thiago10gr.model.Sexo;
import br.com.thiago10gr.model.SimNao;
import br.com.thiago10gr.model.Usuario;
import br.com.thiago10gr.util.DateTime;

public class UsuarioDAO implements Serializable{

	private static final long serialVersionUID = 2795441566542762266L;
	
	public List<Usuario> buscarUsuarios(Connection con) throws SQLException {
		
		List<Usuario> lista = new ArrayList<Usuario>();
		
		String sql = " SELECT U.ID_USUARIO, U.NOME, U.EMAIL, U.SENHA, U.TELEFONE, U.FOTO, U.ATIVO, "
				+ " U.DATA_CADASTRO, U.DATA_NASC, U.SEXO FROM TBL_USUARIO U WHERE 1=1 ";
		
		Usuario usuario = null;  
		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) { 
			
			usuario = new Usuario();
			
			usuario.setId(rs.getInt("ID_USUARIO"));
			
			if(rs.getDate("DATA_NASC") != null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(rs.getDate("DATA_NASC"));
			    usuario.setDataNascimento(calendar); 
			}
		   
		    DateTime dateTime = new DateTime();
		    dateTime.setTime(rs.getTimestamp("DATA_CADASTRO"));
		    usuario.setDataCadastro(dateTime); 
		    
		    usuario.setNome(rs.getString("NOME"));
		    usuario.setAtivo(SimNao.buscaEnum(rs.getString("ATIVO")));
		    usuario.setEmail(rs.getString("EMAIL"));
		    usuario.setTelefone(rs.getString("TELEFONE"));
		    
		    if(rs.getString("SEXO") != null && !"".equals(rs.getString("SEXO"))) {
		    	usuario.setSexo(Sexo.buscaEnum(rs.getString("SEXO")));
		    }
		    if(rs.getBytes("FOTO") != null) {
		    	usuario.setFoto(rs.getBytes("FOTO"));
		    }
			
			lista.add(usuario);
		
		}
			    
		rs.close();
		stmt.close();
			        
		return lista;
	}
	
	
	public Usuario buscarPorEmail(String email, Connection con) throws SQLException {
		
		String sql = " SELECT U.ID_USUARIO, U.NOME, U.EMAIL, U.SENHA, U.TELEFONE, U.ATIVO, "
				+ " U.DATA_CADASTRO FROM TBL_USUARIO U WHERE U.EMAIL = ? ";
		
		int indice = 0;

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(++indice, email);
		
		Usuario usuario = null;  
		ResultSet rs = stmt.executeQuery();
			    
		if (rs.next()) { 
			usuario = new Usuario();
			
			usuario.setId(rs.getInt("ID_USUARIO"));
			
		    DateTime dateTime = new DateTime();
		    dateTime.setTime(rs.getTimestamp("DATA_CADASTRO"));
		    usuario.setDataCadastro(dateTime); 
		    
		    usuario.setNome(rs.getString("NOME"));
		    usuario.setAtivo(SimNao.buscaEnum(rs.getString("ATIVO")));
		    usuario.setEmail(rs.getString("EMAIL"));
		    usuario.setTelefone(rs.getString("TELEFONE"));
		    
		}
			    
		rs.close();
		stmt.close();
			        
		return usuario;
	}
	
	
	public Usuario buscarUsuario(String email, String senha, Connection con) throws SQLException {
		
		String sql = " SELECT U.ID_USUARIO, U.NOME, U.EMAIL, U.SENHA, U.TELEFONE, U.ATIVO, "
				+ " U.DATA_CADASTRO FROM TBL_USUARIO U WHERE U.EMAIL = ? AND U.SENHA = ? ";
		
		int indice = 0;

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(++indice, email);
		stmt.setString(++indice, senha);
		
		Usuario usuario = null;  
		ResultSet rs = stmt.executeQuery();
			    
		if (rs.next()) { 
			usuario = new Usuario();
			
			usuario.setId(rs.getInt("ID_USUARIO"));
			
		    DateTime dateTime = new DateTime();
		    dateTime.setTime(rs.getTimestamp("DATA_CADASTRO"));
		    usuario.setDataCadastro(dateTime); 
		    
		    usuario.setNome(rs.getString("NOME"));
		    usuario.setAtivo(SimNao.buscaEnum(rs.getString("ATIVO")));
		    usuario.setEmail(rs.getString("EMAIL"));
		    usuario.setTelefone(rs.getString("TELEFONE"));
		    
		}
			    
		rs.close();
		stmt.close();
			        
		return usuario;
	}

	/**
	 * registra um usuario com as informacoees basicas 
	 * @param usuario
	 * @param con
	 * @return id gerado pela base de dados
	 * @throws SQLException
	 */
	public int cadastrarUsuario(Usuario usuario, Connection con) throws SQLException {
		
		String sql = " INSERT INTO TBL_USUARIO(NOME, EMAIL, TELEFONE, SENHA, ATIVO, DATA_CADASTRO) "
				+ " VALUES(?, ?, ?, ?, 'S', NOW() )";
	        
		int indice = 0;
	        
	    PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    
	    stmt.setString(++indice, usuario.getNome());
	    stmt.setString(++indice, usuario.getEmail());
	    stmt.setString(++indice, usuario.getTelefone());
	    stmt.setString(++indice, usuario.getSenha());
	    
	    stmt.executeUpdate();
	    
	    ResultSet rs = stmt.getGeneratedKeys();  
        rs.next();  
        int id = rs.getInt(1);  
        
	    stmt.close();
	    rs.close();
	    
	    return id; //retorna o ID gerado 
	        	
	}
	
}	

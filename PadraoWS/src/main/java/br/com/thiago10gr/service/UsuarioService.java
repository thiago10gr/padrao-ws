package br.com.thiago10gr.service;

import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import br.com.thiago10gr.dao.DBUtil;
import br.com.thiago10gr.dao.UsuarioDAO;
import br.com.thiago10gr.model.SimNao;
import br.com.thiago10gr.model.Usuario;
import br.com.thiago10gr.util.Util;

import com.auth0.jwt.Algorithm;

public class UsuarioService implements Serializable{

	private static final long serialVersionUID = 1355982902997591871L;
	
	private UsuarioDAO usuarioDAO = new UsuarioDAO();	

	public String realizarLogin(Usuario usuario) throws Exception {
		Connection con = DBUtil.getConnection();
		Usuario usu = null;
		String token = null;
		try {

			//validando informações 
			validarRealizarLogin(usuario);
			
			usu = usuarioDAO.buscarUsuario(usuario.getEmail().toUpperCase(), Util.sha1(usuario.getSenha()), con);
			
			if(usu == null) {
				throw new ServiceException("Login e/ou senha incorreto.");
			}
			
			if(usu.getAtivo().equals(SimNao.NAO)) {
				throw new ServiceException("Seu login está bloqueado temporariamente.");
			}
		
			long iat = System.currentTimeMillis() / 1000L; // issued at claim
    		long exp = iat + 172800L; // +48 hours
    			
    		System.out.println("Criação: " + iat);
    		System.out.println("Expiração: " + exp);
    			
    		HashMap<String, Object> claims = new HashMap<>();
    			
    		// Reserved Claims ou Claims pre-definidos (Payload)
    		claims.put("sub", String.valueOf(usu.getId())); //(Subject): A entidade a quem este token pertence (geralmente o Id do usuário)
    		claims.put("iss", Util.URL); // (Issuer): Informa da onde o token está vindo.
    		claims.put("iat", iat); // (IssuedAt): Quando o token foi gerado. (unix timestamp)
    		claims.put("exp", exp); // (Expiration): Quando o token expira. (unix timestamp)
    			
    		//claims.put("jti", ""); //JWT Id - Um identificador exclusivo para o token (md5 dos sub e IAT reivindicações)
    		//claims.put("aud", ""); //Audience - O público-alvo para o token (não é necessária por padrão)
    		//claims.put("nbf", ""); //Not Before - O primeiro ponto no tempo que o sinal pode ser usado (unix timestamp)
    			
    		// Claims customizados
    		claims.put("nome", usu.getNome());
    		claims.put("id", String.valueOf(usu.getId()));
    		claims.put("email", usu.getEmail());
    		
    		// Token gerado com o argoritimo HS256
    		token = Util.gerarToken(claims, Algorithm.HS256, Util.SECRET_KEY);
    		
    		if(token == null || "".equals(token)) {
    			throw new ServiceException("Não foi possível realizar o login no momento");
    		}
    		
		} catch (ServiceException e){
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.closeConnection(con);
		}
		return token;
	}

	public void cadastrar(Usuario usuario) throws ServiceException, Exception  {
		Connection con = DBUtil.getConnection();	
		try {
			DBUtil.beginTransaction(con);
			
			//No model há uma verificação se o valor está vazio ou nulo, 
			//não sendo nulo ou vazio passa para maiúsculo 
			usuario.setNome(usuario.getNome());
			usuario.setEmail(usuario.getEmail());
			
			//validando informações
			validarCadastro(usuario);
			
			if(usuarioDAO.buscarPorEmail(usuario.getEmail(), con) != null) {
				throw new ServiceException("E-mail já cadastrado.");
			}
			
			//criptografando
			String senhaCriptografada = Util.sha1(usuario.getSenha());
			//substituindo senha fornecida pela senha criptografada
			usuario.setSenha(senhaCriptografada);
			
			//cadastrando o usuário
			usuarioDAO.cadastrarUsuario(usuario, con);
			
			DBUtil.commit(con);
		} catch (ServiceException e){
			DBUtil.rollback(con);
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			DBUtil.rollback(con);
			throw new Exception(e);
		} finally {
			DBUtil.closeConnection(con);
		}
	}
	
	public List<Usuario> buscarUsuarios() throws Exception {
		Connection con = DBUtil.getConnection();
		List<Usuario> lista = null;
		try {

			lista = usuarioDAO.buscarUsuarios(con);
						
		} catch (Exception e) {
			DBUtil.rollback(con);
			throw new Exception(e);
		} finally {
			DBUtil.closeConnection(con);
		}
		return lista;
	}

	
	private void validarCadastro(Usuario usuario) throws ServiceException {
		if(usuario.getNome() == null || "".equals(usuario.getNome())) {
			throw new ServiceException("Informe o nome");	
		}
		if(!usuario.getNome().matches("^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ'\\s]+$")) {
			throw new ServiceException("Nome inválido");	
		}
		if(usuario.getNome().length() < 5) {
			throw new ServiceException("Nome inválido");
		}
		if(usuario.getEmail() == null || "".equals(usuario.getEmail())) {
			throw new ServiceException("Informe o e-mail");
		}
		if(!usuario.getEmail().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			throw new ServiceException("E-mail inválido");
		}
		if(usuario.getEmail().length() < 5) {
			throw new ServiceException("E-mail inválido");
		}
		if(usuario.getSenha() == null || "".equals(usuario.getSenha())) {
			throw new ServiceException("Informe a senha");
		}
		if(!usuario.getSenha().matches("^[a-zA-Z0-9]+")) {
			throw new ServiceException("Senha inválida");
		}
		if(usuario.getSenha().length() < 6) {
			throw new ServiceException("Senha inválida");
		}
		if(usuario.getTelefone() == null || "".equals(usuario.getTelefone())) {
			throw new ServiceException("Informe o telefone");
		}
		if(!usuario.getTelefone().matches("^\\([0-9]{2}\\)[0-9]{4,5}\\-[0-9]{4}+")) {
			throw new ServiceException("Número de telefone inválido");
		}
	}
	
	private void validarRealizarLogin(Usuario usuario) throws ServiceException {
		if(usuario.getEmail() == null || "".equals(usuario.getEmail())) {
			throw new ServiceException("Informe o e-mail");
		}
		if(!usuario.getEmail().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			throw new ServiceException("E-mail inválido");
		}
		if(usuario.getEmail().length() < 5) {
			throw new ServiceException("E-mail inválido");
		}
		if(usuario.getSenha() == null || "".equals(usuario.getSenha())) {
			throw new ServiceException("Informe a senha");
		}
		if(!usuario.getSenha().matches("^[a-zA-Z0-9]+")) {
			throw new ServiceException("Senha inválida");
		}
		if(usuario.getSenha().length() < 6) {
			throw new ServiceException("Senha inválida");
		}
	}
}
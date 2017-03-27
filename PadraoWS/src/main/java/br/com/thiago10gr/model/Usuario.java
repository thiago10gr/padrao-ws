package br.com.thiago10gr.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;
import br.com.thiago10gr.util.DateTime;

@XmlRootElement(name = "usuario")
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 8978906279526027267L;

	private Integer id;
	
	@FormParam("nome")
	private String nome;
	
	@FormParam("senha")
	private String senha;
	
	@FormParam("telefone")
	private String telefone;
	
	@FormParam("email")
	private String email;
	
	private Calendar dataNascimento;
	private DateTime dataCadastro;
	private SimNao ativo;
	private Sexo sexo;
	private byte[] foto;
	 
	public Usuario() {
		
	}
	 
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Calendar getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if(email != null && !"".equals(email)) {
			this.email = email.toUpperCase();
		} else {
			this.email = email;
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if(nome != null && !"".equals(nome)) {
			this.nome = nome.toUpperCase();
		} else {
			this.nome = nome;
		}
	}

	public DateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(DateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Sexo getSexo() {
		return sexo;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public SimNao getAtivo() {
		return ativo;
	}

	public void setAtivo(SimNao ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
	
}

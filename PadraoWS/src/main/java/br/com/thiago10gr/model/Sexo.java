package br.com.thiago10gr.model;

import com.google.gson.annotations.SerializedName;

public enum Sexo {
	
	@SerializedName("M")
	MASCULINO("M", "Masculino"),
	@SerializedName("F")
	FEMININO("F", "Feminino");
	
	private String valor;
	private String descricao;

	private Sexo(String valor, String descricao) {
		this.valor = valor;
		this.descricao = descricao;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static Sexo buscaEnum(String valor) {
		for (Sexo e : Sexo.values()) {
			if (e.getValor().equals(valor)) {
				return e;
			}
		}
		
		return null;
	}
	

}

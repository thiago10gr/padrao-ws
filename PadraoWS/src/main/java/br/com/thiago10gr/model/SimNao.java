package br.com.thiago10gr.model;

import com.google.gson.annotations.SerializedName;

public enum SimNao {
	
	@SerializedName("S")
	SIM("S", "Sim"),
	@SerializedName("N")
	NAO("N", "Não");
	  
	private String valor;
	private String descricao;
 
	private SimNao(String valor, String descricao) {
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

	public static SimNao buscaEnum(String valor) {
		for (SimNao e : SimNao.values()) {
			if (e.getValor().equals(valor)) {
				return e;
			}
		}
		
		return null;
	}
	
}

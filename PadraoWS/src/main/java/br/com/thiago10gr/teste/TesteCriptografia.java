package br.com.thiago10gr.teste;

import br.com.thiago10gr.util.Util;

public class TesteCriptografia {
	
	public static void main(String[] args) {
		String senhaCriptografada = Util.sha1("2222");
		System.out.println(senhaCriptografada);
	}
	
}

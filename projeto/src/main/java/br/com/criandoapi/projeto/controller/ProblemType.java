package br.com.criandoapi.projeto.controller;

import lombok.Getter;

@Getter
public enum ProblemType {
	DADOS_INVALIDOS("/dados-invalidos", "Dados Inválidos"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-imcompreensivel", "Mensagem Inconpreensivel"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema");
	

	
	private String title;
	private String uri;
	
	ProblemType(String path, String title){
		this.uri = "https://usuarios.com.br"+ path ;
		this.title = title;
	}
}

package br.com.criandoapi.projeto.controller;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Problem {
	
	private Integer status;
	private String type;
	private String title;
	private String detail;
	
	private String userMessage;
	private OffsetDateTime timestamp;
	
	private List<Object> objects;
	
	@Getter
	@Builder
	public static class Object{
		private String nome;
		private String userMessage;
	}

}

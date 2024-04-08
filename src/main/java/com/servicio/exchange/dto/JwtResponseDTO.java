package com.servicio.exchange.dto;

public class JwtResponseDTO {
	
	private String tipo ="Bearer";
	
	private String token;
	
	
	public JwtResponseDTO(String token) {
		this.token=token;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}
	
	
	
	

}

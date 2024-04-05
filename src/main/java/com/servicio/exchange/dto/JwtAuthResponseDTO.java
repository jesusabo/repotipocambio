package com.servicio.exchange.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthResponseDTO {

	private String tokenDeAcceso;
	
	private String tipoDeToken="Bearer";
	
	
	public JwtAuthResponseDTO(String token) {
		this.tokenDeAcceso = token;
	}
	
}

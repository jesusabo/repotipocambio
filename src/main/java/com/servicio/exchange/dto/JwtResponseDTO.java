package com.servicio.exchange.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponseDTO {
	
	private String tipoToken = "Bearer";
	
	private String token;
	
	public JwtResponseDTO(String token) {
		this.token=token;
	}
}

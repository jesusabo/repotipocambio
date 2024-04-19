package com.servicio.exchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtResponseDTO {

	private String tipoToken="Bearer";
	
	private String token;
	
	
	public JwtResponseDTO(String token) {
		this.token = token;
	}
	
}

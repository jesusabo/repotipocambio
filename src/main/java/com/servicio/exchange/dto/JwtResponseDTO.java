package com.servicio.exchange.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtResponseDTO {

	
	private String tipo="Token";
	
	private String valor;
	
	public JwtResponseDTO(String valor) {
		this.valor=valor;
	}
}

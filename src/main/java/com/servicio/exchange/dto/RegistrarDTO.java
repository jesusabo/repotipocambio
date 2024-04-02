package com.servicio.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrarDTO {
	
	private String name;
	
	private String username;
	
	private String email;
	
	private String password;
	
	private String tipo;

}

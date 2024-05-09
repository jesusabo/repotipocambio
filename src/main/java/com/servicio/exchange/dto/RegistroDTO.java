package com.servicio.exchange.dto;

import lombok.Setter;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroDTO {
	
	@Size(min = 3, message = "El nombre debe tener al menos 3 caracteres")
	private String name;
	
	private String username;
	
	private String email;
	
	private String password;
	
	private String tipo;

}

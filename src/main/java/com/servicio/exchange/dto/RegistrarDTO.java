package com.servicio.exchange.dto;

import lombok.AllArgsConstructor;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
@AllArgsConstructor
public class RegistrarDTO {
	
	private String username;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String tipo;

}

package com.servicio.exchange.dto;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarDTO {
	
	private String name;
	
	private String username;
	
	private String email;
	
	private String password;
	
	private String tipo;

}

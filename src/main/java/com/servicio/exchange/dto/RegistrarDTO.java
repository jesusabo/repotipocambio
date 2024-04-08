package com.servicio.exchange.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class RegistrarDTO {

	@Size(min = 4, message = "El usuario debe tener al menos 4 caracteres")
	private String name;
	
	@Size(min = 5, message = "El username debe tener al menos 5 caracteres")
	private String username;
	
	@Email(message = "El email debe tener un formato correcto")
	private String email;
	
	@Size(min=6, message = "La contraseña debe tener al menos 8 caracteres")
	private String password;
	
	@NotEmpty(message = "El tipo no puede estar vacio")
	private String tipo;
	
}

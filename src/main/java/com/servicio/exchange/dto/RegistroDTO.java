package com.servicio.exchange.dto;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroDTO {
	
	@Size(min = 3, message = "Username debe tener al menos 3 caracteres")
	private String username;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String tipo;

	@Override
	public String toString() {
		return "RegistroDTO [username=" + username + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", tipo=" + tipo + "]";
	}
	
	

}

package com.servicio.exchange.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String recurso;
	
	public ResourceNotFoundException(String recurso) {
		super(String.format("Recurso %s no encontrado", recurso));
	}
	
	


}

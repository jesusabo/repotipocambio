package com.servicio.exchange.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundExceptions extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String entidad;
	private String recurso;
	
	public ResourceNotFoundExceptions(String entidad, String recurso) {
		super(String.format("Recurso %s con name %s no encontrado", entidad, recurso));
	}
	
	
	

}

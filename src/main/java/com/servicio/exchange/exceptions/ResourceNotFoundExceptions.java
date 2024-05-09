package com.servicio.exchange.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundExceptions extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	
	private String recurso;
	
	private String tipo;
	
	private String valor;
	
	public ResourceNotFoundExceptions(String recurso, String tipo, String valor) {
		super(String.format("%s con %s : [ %s ] no encontrado", recurso,tipo,valor));
	}

}

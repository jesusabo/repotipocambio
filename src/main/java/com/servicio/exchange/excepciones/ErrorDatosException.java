package com.servicio.exchange.excepciones;

import org.springframework.validation.BindingResult;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ErrorDatosException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private BindingResult descripcion;
	
	public ErrorDatosException() {
		
	}
	

	
	public ErrorDatosException(BindingResult result) {
		this.descripcion = result;
	}

}

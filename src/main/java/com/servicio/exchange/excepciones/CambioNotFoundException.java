package com.servicio.exchange.excepciones;

public class CambioNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	private String tipoCambio;

	public CambioNotFoundException(String tipoCambioOrigin, String tipoCambioDestino) {
		super(String.format("El tipo de cambio de %s a %s no se encuentra registrado", tipoCambioOrigin,tipoCambioDestino));
		this.tipoCambio=tipoCambio;
	}

	
	

}

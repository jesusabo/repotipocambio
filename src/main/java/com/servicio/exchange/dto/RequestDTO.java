package com.servicio.exchange.dto;

public class RequestDTO {
	
	private String monedaOrigen;
	
	private String monedaDestino;
	
	private Double tipoCambioNuevo;

	public String getMonedaOrigen() {
		return monedaOrigen;
	}

	public void setMonedaOrigen(String monedaOrigen) {
		this.monedaOrigen = monedaOrigen;
	}

	public String getMonedaDestino() {
		return monedaDestino;
	}

	public void setMonedaDestino(String monedaDestino) {
		this.monedaDestino = monedaDestino;
	}

	public Double getTipoCambioNuevo() {
		return tipoCambioNuevo;
	}

	public void setTipoCambioNuevo(Double tipoCambioNuevo) {
		this.tipoCambioNuevo = tipoCambioNuevo;
	}	
	
	

}

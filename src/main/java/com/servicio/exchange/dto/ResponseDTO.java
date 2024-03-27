package com.servicio.exchange.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ResponseDTO {

	private String monedaOrigen;
	private String monedaDestino;
	
	@JsonInclude(Include.NON_NULL)
	private Double montoOrigen;
	
	private Double tipoCambio;
	
	@JsonInclude(Include.NON_NULL)
	private Double montoAlCambio;
	
	@JsonInclude(Include.NON_NULL)
	private Double nuevoTipoCambio;
	
	public ResponseDTO() {
		
	}
	
	public ResponseDTO(String monedaOrigen, String monedaDestino, Double montoOrigen, Double tipoCambio,
			Double montoAlCambio) {
		super();
		this.monedaOrigen = monedaOrigen;
		this.monedaDestino = monedaDestino;
		this.montoOrigen = montoOrigen;
		this.tipoCambio = tipoCambio;
		this.montoAlCambio = montoAlCambio;
	}
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
		this.monedaDestino=monedaDestino;
	}
	public Double getMontoOrigen() {
		return montoOrigen;
	}
	public void setMontoOrigen(Double montoOrigen) {
		this.montoOrigen = montoOrigen;
	}
	public Double getTipoCambio() {
		return tipoCambio;
	}
	public void setTipoCambio(Double tipoCambio) {
		this.tipoCambio = tipoCambio;
	}
	public Double getMontoAlCambio() {
		return montoAlCambio;
	}
	public void setMontoAlCambio(Double montoAlCambio) {
		this.montoAlCambio = montoAlCambio;
	}

	public Double getNuevoTipoCambio() {
		return nuevoTipoCambio;
	}

	public void setNuevoTipoCambio(Double nuevoTipoCambio) {
		this.nuevoTipoCambio = nuevoTipoCambio;
	}
	
	
}

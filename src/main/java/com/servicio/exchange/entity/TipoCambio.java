package com.servicio.exchange.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="tipo_camb", uniqueConstraints = @UniqueConstraint(columnNames = {"moneda_origen","moneda_destino"}))
public class TipoCambio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="moneda_origen")
	private String from;
	
	@Column(name="moneda_destino")
	private String to;
	
	@Column(name="multiplo_cambio")
	private Double multiploCambio;
	
	public TipoCambio() {
		
	}

	public TipoCambio(Long id, String from, String to, Double multiploCambio) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.multiploCambio = multiploCambio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Double getMultiploCambio() {
		return multiploCambio;
	}

	public void setMultiploCambio(Double multiploCambio) {
		this.multiploCambio = multiploCambio;
	}

	@Override
	public String toString() {
		return "TipoCambio [id=" + id + ", from=" + from + ", to=" + to + ", multiploCambio=" + multiploCambio + "]";
	}
	
	
	
}

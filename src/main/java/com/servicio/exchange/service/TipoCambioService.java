package com.servicio.exchange.service;

import java.util.List;

import com.servicio.exchange.entity.TipoCambio;

public interface TipoCambioService {
	
	TipoCambio findByFromAndTo(String monedaOrigin, String monedaDestino);
	
	TipoCambio save(TipoCambio tipoCambio);
	
	List<TipoCambio> findAll();

}

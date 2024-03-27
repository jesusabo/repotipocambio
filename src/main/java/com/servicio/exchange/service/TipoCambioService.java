package com.servicio.exchange.service;

import com.servicio.exchange.entity.TipoCambio;

public interface TipoCambioService {
	
	TipoCambio findByFromAndTo(String monedaOrigin, String monedaDestino);
	
	TipoCambio save(TipoCambio tipoCambio);

}

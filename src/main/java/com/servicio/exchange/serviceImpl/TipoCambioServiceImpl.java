package com.servicio.exchange.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.exchange.entity.TipoCambio;
import com.servicio.exchange.repository.TipoCambioRepository;
import com.servicio.exchange.service.TipoCambioService;

@Service
public class TipoCambioServiceImpl implements TipoCambioService{

	@Autowired
	private TipoCambioRepository tipoCambioRepository;
	
	@Override
	public TipoCambio findByFromAndTo(String monedaOrigin, String monedaDestino) {
		return tipoCambioRepository.findByFromAndTo(monedaOrigin, monedaDestino);
	}

	@Override
	public TipoCambio save(TipoCambio tipoCambio) {
		return tipoCambioRepository.save(tipoCambio);
	}

}

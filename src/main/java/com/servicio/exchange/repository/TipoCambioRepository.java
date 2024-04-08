package com.servicio.exchange.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicio.exchange.entity.TipoCambio;


@Repository
public interface TipoCambioRepository extends JpaRepository<TipoCambio, Long>{

	Optional<TipoCambio> findByFromAndTo(String from, String to);
}

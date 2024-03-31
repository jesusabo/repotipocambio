package com.servicio.exchange.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicio.exchange.entity.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long>{
	
	Optional<Rol> findByNombre(String name);

}

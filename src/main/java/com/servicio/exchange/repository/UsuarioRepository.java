package com.servicio.exchange.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servicio.exchange.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findByName(String name);
	
	Optional<Usuario> findByUsername(String name);
	
	Optional<Usuario> findByUsernameOrEmail(String name, String email);
	
	Optional<Usuario> findByEmail(String email);
	
	Boolean existsByEmail(String email);
	
	Boolean existsByUsername(String username);

}

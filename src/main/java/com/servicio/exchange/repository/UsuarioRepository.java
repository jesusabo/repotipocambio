package com.servicio.exchange.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicio.exchange.entity.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findByName(String name);
	
	Optional<Usuario> findByUsername(String username);
	
	Optional<Usuario> findByUsernameOrEmail(String username, String password);
	
	Optional<Usuario> findByEmail(String email);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	

}

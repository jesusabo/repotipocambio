package com.servicio.exchange.service;

import java.util.List;

import com.servicio.exchange.entity.Usuario;

public interface UsuarioService {
	
	Usuario save(Usuario usuario);
	
	Usuario findByName(String name);
	
	Usuario findByUsername(String username);
	
	Usuario findByUsernameOrEmail(String username, String password);
	
	Usuario findByEmail(String email);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	
	List<Usuario> findAll();
	
	void deleteByUsername(String username);	

}

package com.servicio.exchange.service;

import com.servicio.exchange.entity.Usuario;

public interface UsuarioService {
	
	Usuario save(Usuario usuario);
	
	Usuario findByUsername(String username);
	
	Usuario findByName(String name);
	
	Usuario findByEmail(String email);
	
	Usuario findByUsernameOrEmail(String username, String email);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
}

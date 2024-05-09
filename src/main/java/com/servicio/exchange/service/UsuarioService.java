package com.servicio.exchange.service;

import com.servicio.exchange.entity.Usuario;

public interface UsuarioService {
	
	Usuario findByName(String name);
	Usuario findByUsername(String username);
	Usuario findByEmail(String email);
	Usuario findByUsernameOrPassword(String username, String password);
	Boolean existsByEmail(String email);
	Boolean existsByUsername(String username);
	Usuario save(Usuario usuario);

}

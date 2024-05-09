package com.servicio.exchange.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.exchange.entity.Usuario;
import com.servicio.exchange.exceptions.ResourceNotFoundExceptions;
import com.servicio.exchange.repository.UsuarioRepository;
import com.servicio.exchange.service.UsuarioService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Usuario findByName(String name) {
		return usuarioRepository.findByName(name).orElseThrow(()-> new ResourceNotFoundExceptions("Usuario", "name", name));
	}

	@Override
	public Usuario findByUsername(String username) {
		return usuarioRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundExceptions("Usuario", "name", username));
	}

	@Override
	public Usuario findByEmail(String email) {
		return usuarioRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundExceptions("Usuario", "name", email));
	}

	@Override
	public Usuario findByUsernameOrPassword(String username, String password) {
		log.info("[[ findByUsernameOrPassword [ username : "+username);
		return usuarioRepository.findByUsernameOrEmail(username, password).orElseThrow(()-> new ResourceNotFoundExceptions("Usuario", "username o password", username));
	}

	@Override
	public Boolean existsByEmail(String email) {
		return usuarioRepository.existsByEmail(email);
	}

	@Override
	public Boolean existsByUsername(String username) {
		return usuarioRepository.existsByUsername(username);
	}

	@Override
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

}

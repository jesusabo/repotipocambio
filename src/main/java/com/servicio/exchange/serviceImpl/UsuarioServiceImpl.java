package com.servicio.exchange.serviceImpl;

import java.util.List;
import java.util.Optional;

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
		return usuarioRepository.findByName(name).orElse(null);
	}

	@Override
	public Usuario findByUsernameOrEmail(String username, String password) {
		return usuarioRepository.findByUsernameOrEmail(username, password).orElse(null);
	}

	@Override
	public Usuario findByEmail(String email) {
		return usuarioRepository.findByEmail(email).orElse(null);
	}

	@Override
	public Boolean existsByUsername(String username) {
		return usuarioRepository.existsByUsername(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return usuarioRepository.existsByEmail(email);
	}

	@Override
	public Usuario findByUsername(String username) {
		return usuarioRepository.findByUsername(username).orElse(null);
	}

	@Override
	public Usuario save(Usuario usuario) {
		log.info("[[ save [ guardando usuaurio");
		
		Optional<Usuario> usuarioOp = usuarioRepository.findByEmail(usuario.getEmail());
		if(usuarioOp.isPresent()) {
			log.info("[[ save [ usuario con email ya se encuentra");
			throw new ResourceNotFoundExceptions("Usuario", usuario.getEmail());
		}
		
		return usuarioRepository.save(usuario);
	}

	@Override
	public List<Usuario> findAll() {
		 return usuarioRepository.findAll();
	}

	@Override
	public void deleteByUsername(String username) {
		
		log.info("[[ deleteByUsername [ username: "+username);
		
		Optional<Usuario> usuarioOp = usuarioRepository.findByUsername(username);
		if(usuarioOp.isPresent()) {
			Usuario usuario = usuarioOp.get();
			log.info("[[ deleteByUsername [ usuario encontrado: "+usuario.getUsername());
			usuarioRepository.delete(usuario);
			
		}else {
			throw new ResourceNotFoundExceptions("Usuario", username);
		}
		
		
	}

}

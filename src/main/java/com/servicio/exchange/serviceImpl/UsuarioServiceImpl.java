package com.servicio.exchange.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.exchange.entity.Usuario;
import com.servicio.exchange.repository.UsuarioRepository;
import com.servicio.exchange.service.UsuarioService;


@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	

	@Override
	public Usuario findByUsername(String username) {
		Optional<Usuario> usuarioOp = usuarioRepository.findByUsername(username);
		
		if(usuarioOp.isPresent()) {
			return usuarioOp.get();
		}
		return null;
	}

	@Override
	public Usuario findByName(String name) {
		
		Optional<Usuario> usuarioOp = usuarioRepository.findByName(name);
		
		if(usuarioOp.isPresent()) {
			return usuarioOp.get();
		}
		
		return null;
	}

	@Override
	public Usuario findByEmail(String email) {

		Optional<Usuario> usuarioOp = usuarioRepository.findByEmail(email);
		
		if(usuarioOp.isPresent()) {
			return usuarioOp.get();
		}
		return null;
	}

	@Override
	public Usuario findByUsernameOrEmail(String username, String email) {
	
		Optional<Usuario> usuarioOp = usuarioRepository.findByUsernameOrEmail(username, email);
		
		if(usuarioOp.isPresent()) {
			return usuarioOp.get();
		}
		return null;
	}

	@Override
	public Boolean existsByUsername(String username) {
		
		Boolean usuarioOp = usuarioRepository.existsByUsername(username);
		
		return usuarioOp;
	}

	@Override
	public Boolean existsByEmail(String email) {

		Boolean usuarioOp = usuarioRepository.existsByEmail(email);
		return usuarioOp;
	}

	@Override
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

}

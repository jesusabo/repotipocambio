package com.servicio.exchange.seguridad;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.servicio.exchange.entity.Rol;
import com.servicio.exchange.entity.Usuario;
import com.servicio.exchange.repository.UsuarioRepository;

@Service
public class CustomDetailsService implements UserDetailsService{
	
	
	private static final Logger log = LoggerFactory.getLogger(CustomDetailsService.class);

	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	
		log.info("loadUserByUsername: ["+username+"]");
		Usuario usuario = usuarioRepository.findByUsernameOrEmail(username, username).orElseThrow(()-> new RuntimeException("Usuario no existe"));
		return new User(usuario.getEmail(), usuario.getPassword(), mapearRoles(usuario.getRoles()));	
	}
	
	private Collection<? extends GrantedAuthority> mapearRoles(Set<Rol> roles){
		return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList());
	}

}

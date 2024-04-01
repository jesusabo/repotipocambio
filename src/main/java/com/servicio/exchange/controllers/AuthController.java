package com.servicio.exchange.controllers;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicio.exchange.dto.LoginDTO;
import com.servicio.exchange.dto.RegistroDTO;
import com.servicio.exchange.entity.Rol;
import com.servicio.exchange.entity.Usuario;
import com.servicio.exchange.repository.RolRepository;
import com.servicio.exchange.repository.UsuarioRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RolRepository rolRepository;
	
	@PostMapping("/iniciarSesion")
	public ResponseEntity<String> iniciarSesion(@RequestBody LoginDTO login){
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseEntity<>("Sesion iniciada correctamente", HttpStatus.OK);
	}
	
	@PostMapping("/registrar")
	public ResponseEntity<String> registrar(@RequestBody RegistroDTO registro){
		
		if(usuarioRepository.existsByUsername(registro.getUsername())) {
			return new ResponseEntity<String>("Usuario ya existe", HttpStatus.BAD_REQUEST);
		}
		
		if(usuarioRepository.existsByEmail(registro.getEmail())) {
			return new ResponseEntity<String>("Correo ya se encuentra registrado", HttpStatus.BAD_REQUEST);
		}
		
		Usuario usuario = new Usuario();
		usuario.setUsername(registro.getUsername());
		usuario.setName(registro.getName());
		usuario.setEmail(registro.getEmail());
		usuario.setPassword(passwordEncoder.encode(registro.getPassword()));
		
		Rol roles;
		
		if(registro.getTipo().equals("admin")) {
			roles = rolRepository.findByNombre("ROLE_ADMIN").get();
			usuario.setRoles(Collections.singleton(roles));
			usuario.setRoles(Collections.singleton(roles));
		}else if(registro.getTipo().equals("user")) {
			roles = rolRepository.findByNombre("ROLE_USER").get();
			usuario.setRoles(Collections.singleton(roles));
			usuario.setRoles(Collections.singleton(roles));
		}else {
			List<Rol> rolesList = rolRepository.findAll();
			Set<Rol> rolesSet = new HashSet<Rol>(rolesList);
			usuario.setRoles(rolesSet);
		}
		
		
		
		usuarioRepository.save(usuario);
		
		return new ResponseEntity<String>("Usuario Registrado Correctamente", HttpStatus.OK);
		
		
	}

}

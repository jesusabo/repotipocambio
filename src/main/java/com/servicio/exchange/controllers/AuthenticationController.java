package com.servicio.exchange.controllers;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.servicio.exchange.dto.JwtAuthResponseDTO;
import com.servicio.exchange.dto.LoginDTO;
import com.servicio.exchange.dto.RegistroDTO;
import com.servicio.exchange.entity.Rol;
import com.servicio.exchange.entity.Usuario;
import com.servicio.exchange.repository.RolRepository;
import com.servicio.exchange.repository.UsuarioRepository;
import com.servicio.exchange.seguridad.JwtTokenProvider;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	
	private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);


	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RolRepository rolRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@PostMapping
	public ResponseEntity<JwtAuthResponseDTO> iniciarSesion(@RequestBody LoginDTO login){
		
		log.info("[[iniciarSesion ");
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtTokenProvider.generarToken(authentication);
		
		log.info("]] iniciarSesion ");
		
		return new ResponseEntity<JwtAuthResponseDTO>(new JwtAuthResponseDTO(token),HttpStatus.OK);
		
	}
	
	
	@PostMapping("/registrar")
	public ResponseEntity<String> registrar(@RequestBody RegistroDTO registro){
		log.info("[[registrar");
		if(usuarioRepository.existsByUsername(registro.getUsername())) {
			return new ResponseEntity<String>("Usuario ya se encuentra registrado", HttpStatus.BAD_REQUEST);
		}
		
		if(usuarioRepository.existsByEmail(registro.getEmail())) {
			return new ResponseEntity<String>("Email ya se encuentra registrado", HttpStatus.BAD_REQUEST);
		}
		
		Usuario usuario = new Usuario();
		usuario.setName(registro.getName());
		usuario.setUsername(registro.getUsername());
		usuario.setEmail(registro.getEmail());
		usuario.setPassword(passwordEncoder.encode(registro.getPassword()));
		
		Rol rol=null;
		if(registro.getTipo().equals("admin")) {
			rol = rolRepository.findByName("ROLE_ADMIN").get();
		}else if(registro.getTipo().equals("user")) {
			rol = rolRepository.findByName("ROLE_USER").get();
		}
		
		usuario.setRoles(Collections.singleton(rol));
		
		usuarioRepository.save(usuario);
		log.info("]]registrar");
		return new ResponseEntity<String>("Usuario registrado exitosamente", HttpStatus.OK);
		
	}
	
	
}

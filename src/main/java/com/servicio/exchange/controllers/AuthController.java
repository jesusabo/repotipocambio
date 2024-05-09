package com.servicio.exchange.controllers;

import java.util.Collections;

import javax.validation.Valid;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicio.exchange.dto.JwtResponseDTO;
import com.servicio.exchange.dto.LoginDTO;
import com.servicio.exchange.dto.RegistroDTO;
import com.servicio.exchange.entity.Rol;
import com.servicio.exchange.entity.Usuario;
import com.servicio.exchange.seguridad.JwtTokenProvider;
import com.servicio.exchange.service.RolService;
import com.servicio.exchange.service.UsuarioService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	
	@PostMapping("/iniciarSesion")
	public ResponseEntity<JwtResponseDTO> iniciarSesion(@RequestBody LoginDTO loginDTO){
		
		log.info("[[ Iniciar Sesion: "+loginDTO.toString());
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
				
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtTokenProvider.generarToken(authentication);
		
		log.info("[[ Iniciar Sesion [ Token creado correctamente: "+token);
		
		return new ResponseEntity<>(new JwtResponseDTO(token),HttpStatus.OK);
	}
	
	
	@PostMapping("/registrar")
	public ResponseEntity<String> registrar(@RequestBody @Valid RegistroDTO registroDTO){
		
		log.info("Registrar usuario: "+registroDTO.toString());
		
		if(usuarioService.existsByUsername(registroDTO.getUsername()) ) {
			return new ResponseEntity<String>("Username ya se encuentra registrado", HttpStatus.BAD_REQUEST);
		}
		
		if(usuarioService.existsByEmail(registroDTO.getEmail())) {
			return new ResponseEntity<String>("Email ya se encuentra registrado", HttpStatus.BAD_REQUEST);
		}
		
		Rol rol = new Rol();
		if(registroDTO.getTipo().equalsIgnoreCase("admin")) {
			log.info("tipo admin");
			rol = rolService.findByName("ROLE_ADMIN");
		}else {
			log.info("tipo user");
			rol = rolService.findByName("ROLE_USER");
		}
		

		Usuario usuario = new Usuario();
		usuario.setName(registroDTO.getName());
		usuario.setUsername(registroDTO.getUsername());
		usuario.setEmail(registroDTO.getEmail());
		usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
		usuario.setRoles(Collections.singleton(rol));
		log.info(">>>: "+usuario.toString());
		usuarioService.save(usuario);
		
		return new ResponseEntity<String>("Usuario creado correctamente", HttpStatus.OK);
		
		
	}

}

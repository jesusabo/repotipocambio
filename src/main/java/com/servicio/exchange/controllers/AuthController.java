package com.servicio.exchange.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

import com.servicio.exchange.dto.JwtResponseDTO;
import com.servicio.exchange.dto.LoginDTO;
import com.servicio.exchange.dto.RegistroDTO;
import com.servicio.exchange.entity.Rol;
import com.servicio.exchange.entity.Usuario;
import com.servicio.exchange.seguridad.JwtTokenGenerator;
import com.servicio.exchange.service.RolService;
import com.servicio.exchange.service.UsuarioService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenGenerator jwtTokenGenerator;
	
	
	@PostMapping("/iniciarSesion")
	public ResponseEntity<JwtResponseDTO> iniciarSesion(@RequestBody LoginDTO login){
		log.info("[[ iniciarSesion [ username: "+login.getUsername()+" password: "+login.getPassword());
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
		
		String token = jwtTokenGenerator.generarToken(authentication);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);		
		log.info("[[ iniciarSesion [ SecurityContextHolder: "+SecurityContextHolder.getContext().getAuthentication().getName());
		
		return new ResponseEntity<>(new JwtResponseDTO(token), HttpStatus.OK);
	}
	
	@PostMapping("/registrar")
	public ResponseEntity<String> registrar(@RequestBody @Valid RegistroDTO registrar){
		log.info("[[ registrar [ registro: "+registrar.toString());
		
		if(usuarioService.existsByUsername(registrar.getUsername())){
			return new ResponseEntity<String>("Username ya existe", HttpStatus.BAD_REQUEST);
		}
		
		if(usuarioService.existsByEmail(registrar.getEmail())) {
			return new ResponseEntity<String>("Email ya existe", HttpStatus.BAD_REQUEST);
		}
		
		Usuario usuario = new Usuario();
		usuario.setName(registrar.getName());
		usuario.setUsername(registrar.getUsername());
		usuario.setEmail(registrar.getEmail());
		usuario.setPassword(passwordEncoder.encode(registrar.getPassword()));
		
		log.info("[[ registrar [ usuario: "+usuario.toString());
		
		Rol rol = new Rol();
		rol = rolService.findByName("ROLE_ADMIN");
		usuario.setRoles(Collections.singleton(rol));
		
		
		
		usuarioService.save(usuario);
		log.info("]] registrar ]");
		return new ResponseEntity<String>("Usuario creado correctamente", HttpStatus.OK);
		
	}

}

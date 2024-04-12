package com.servicio.exchange.controllers;

import java.util.Collections;

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
import com.servicio.exchange.dto.RegistrarDTO;
import com.servicio.exchange.entity.Rol;
import com.servicio.exchange.entity.Usuario;
import com.servicio.exchange.seguridad.JwtAuthenticationFilter;
import com.servicio.exchange.seguridad.JwtTokenProvider;
import com.servicio.exchange.service.RolService;
import com.servicio.exchange.service.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/iniciarSesion")
	public ResponseEntity<JwtResponseDTO> iniciarSesion(@RequestBody LoginDTO login){
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
		
		String token = jwtTokenProvider.generarToken(authentication);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		
		return new ResponseEntity<JwtResponseDTO>(new JwtResponseDTO(token), HttpStatus.OK);		
	}
	
	@PostMapping("/registrar")
	public ResponseEntity<String> registrarUsuario(@RequestBody RegistrarDTO registrar){
		
		if(usuarioService.existsByUsername(registrar.getUsername())) {
			return new ResponseEntity<String>("Username ya se encuentra registrado", HttpStatus.BAD_REQUEST);
		}
		
		if(usuarioService.existsByEmail(registrar.getEmail())) {
			return new ResponseEntity<String>("Email ya se encuentra registrado", HttpStatus.BAD_REQUEST);
		}
		
		Usuario usuario = new Usuario();
		usuario.setName(registrar.getName());
		usuario.setUsername(registrar.getUsername());
		usuario.setEmail(registrar.getEmail());
		usuario.setPassword(passwordEncoder.encode(registrar.getPassword()));
		
		Rol rol = null;		
		rol = rolService.findByName("ROLE_ADMIN");
		
		usuario.setRoles(Collections.singleton(rol));
		
		usuarioService.save(usuario);	
		
		return new ResponseEntity<String>("Usuario registrado correctamente", HttpStatus.OK);
	}
	

}

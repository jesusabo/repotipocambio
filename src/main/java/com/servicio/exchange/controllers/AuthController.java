package com.servicio.exchange.controllers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.servicio.exchange.dto.RegistrarDTO;
import com.servicio.exchange.entity.Rol;
import com.servicio.exchange.entity.Usuario;
import com.servicio.exchange.repository.RolRepository;
import com.servicio.exchange.repository.UsuarioRepository;
import com.servicio.exchange.seguridad.JwtTokenProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

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
	
	
	
	@PostMapping("/iniciarSesion")
	private ResponseEntity<JwtAuthResponseDTO> iniciarSesion(@RequestBody LoginDTO login){
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
		
//		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token= jwtTokenProvider.generarToken(authentication);
		
		return new ResponseEntity<JwtAuthResponseDTO>(new JwtAuthResponseDTO(token), HttpStatus.OK);
	}
	
	
	@PostMapping("/registrar")
	private ResponseEntity<String> registrar(@RequestBody RegistrarDTO registrar){
		log.info("Registrar");
		if(usuarioRepository.existsByUsername(registrar.getUsername())) {
			return new ResponseEntity<String>("Usuario ya se encuentra registrado",HttpStatus.BAD_REQUEST);
		}
		log.info("Registrar: "+registrar.toString());
		if(usuarioRepository.existsByEmail(registrar.getEmail())) {
			return new ResponseEntity<String>("Email ya se encuentra registrado", HttpStatus.BAD_REQUEST);
		}
		
		Usuario usuario = new Usuario();
		usuario.setName(registrar.getName());
		usuario.setUsername(registrar.getUsername());
		usuario.setEmail(registrar.getEmail());
		usuario.setPassword(passwordEncoder.encode(registrar.getPassword()));
		
		Rol rol = new Rol();
		
		if(registrar.getTipo().equalsIgnoreCase("admin")) {
			rol = rolRepository.findByName("ROLE_ADMIN").get();
		}else{
			rol = rolRepository.findByName("ROLE_USER").get();
		}
		Set<Rol> roles = new HashSet<>();
		roles.add(rol);
		usuario.setRoles(roles);
		//usuario.setRoles(Collections.singleton(rol));
	
		usuarioRepository.save(usuario);
		
		return new ResponseEntity<String>("Usuario creado correctamente",HttpStatus.OK);
	}
	
	
}

package com.servicio.exchange.controllers;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class AuthenticationController {
	
	
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
	public ResponseEntity<JwtResponseDTO> iniciarSesion(@RequestBody LoginDTO login){
		log.info("[[ iniciar sesion");
		Authentication authenticacion = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
		log.info("[[ iniciar sesion [ objeto Authentication construido con valores"+authenticacion);
		SecurityContextHolder.getContext().setAuthentication(authenticacion);
		
		String token = jwtTokenProvider.generarToken(authenticacion);
		
		log.info("[[ iniciar sesion [ token"+token);
		
		return new ResponseEntity<>(new JwtResponseDTO(token), HttpStatus.OK);
		
	}
	
	
	@PostMapping("/registrar")
	public ResponseEntity<String> registrar(@RequestBody @Valid RegistrarDTO registrar){
		
//		if(result.hasErrors()) {
//			throw new ErrorDatosException(result);
//		}
		
		log.info("[[ registrar usuario: "+registrar.toString());
		
		if(usuarioRepository.existsByUsername(registrar.getUsername())) {
			return new ResponseEntity<String>("Usuario ya se encuentra registrado", HttpStatus.BAD_REQUEST);
		}
		
		if(usuarioRepository.existsByEmail(registrar.getEmail())) {
			return new ResponseEntity<String>("Email ya se encuentra registrado", HttpStatus.BAD_REQUEST);
		}
		
		Usuario usuario = new Usuario();
		usuario.setName(registrar.getName());
		usuario.setUsername(registrar.getUsername());
		usuario.setEmail(registrar.getEmail());
		usuario.setPassword(passwordEncoder.encode(registrar.getPassword()));
	
		Rol rol=null;
		if(registrar.getTipo().equalsIgnoreCase("admin")) {
			rol = rolRepository.findByName("ROLE_ADMIN").get();
		}
		else if(registrar.getTipo().equalsIgnoreCase("user")){
			rol = rolRepository.findByName("ROLE_USER").get();
		}else{
			List<Rol> rolesList = rolRepository.findAll();
			Set<Rol> rolesSet = new HashSet<>(rolesList);
			
		}
				
		usuario.setRoles(Collections.singleton(rol));
		
		usuarioRepository.save(usuario);
		
		return new ResponseEntity<String>("Usuario registrado correctamente", HttpStatus.OK);
	}

}

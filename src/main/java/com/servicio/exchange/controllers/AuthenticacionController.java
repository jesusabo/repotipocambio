package com.servicio.exchange.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.servicio.exchange.dto.LoginDTO;
import com.servicio.exchange.dto.RegistrarDTO;
import com.servicio.exchange.entity.Rol;
import com.servicio.exchange.entity.Usuario;
import com.servicio.exchange.repository.RolRepository;
import com.servicio.exchange.repository.UsuarioRepository;
import com.servicio.exchange.seguridad.JWTTokenProvider;

@RestController
@RequestMapping("/auth")
public class AuthenticacionController {
	
	private static final Logger log = LoggerFactory.getLogger(AuthenticacionController.class);
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RolRepository rolRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTTokenProvider jwtTokenProvider;
	
	
	@PostMapping("/iniciarSesion")
	public ResponseEntity<String> iniciarSesion(@RequestBody LoginDTO login){
		
		log.info("[[ iniciarSesion");
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(),login.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtTokenProvider.generarToken(authentication);
		
		log.info("]] iniciarSesion");
		return new ResponseEntity<String>("Sesion iniciada correctamente con JWT: "+token, HttpStatus.OK);
		
	}
	
	@PostMapping("/registrar")
	public ResponseEntity<String> registrarUsuario(@RequestBody RegistrarDTO registrar){
				
		log.info("[[ registrar");
		
		if(usuarioRepository.existsByUsername(registrar.getUsername())) {
			return new ResponseEntity<String>("Usuario ya se encuentra registrado",HttpStatus.BAD_REQUEST);
		}
		
		if(usuarioRepository.existsByEmail(registrar.getEmail())) {
			return new ResponseEntity<String>("Email ya se encuentra registrado", HttpStatus.BAD_REQUEST);
		}
		
		Usuario usuario = new Usuario();
		usuario.setUsername(registrar.getUsername());
		usuario.setName(registrar.getName());
		usuario.setPassword(passwordEncoder.encode(registrar.getPassword()));
		usuario.setEmail(registrar.getEmail());
		
		List<Rol> roles = new ArrayList<>();
		
		if(registrar.getTipo().equals("admin")) {

			roles.add(rolRepository.findByName("ROLE_ADMIN").get());
		}else if(registrar.getTipo().equals("user")) {
			roles.add(rolRepository.findByName("ROLE_USER").get());
		}else {
			roles = rolRepository.findAll();
		}
		Set<Rol> rolesSet = new HashSet<>(roles);
		usuario.setRoles(rolesSet);
		
		usuarioRepository.save(usuario);
		
		log.info("]] registrar");
		
		return new ResponseEntity<String>("Usuario Registrado Correctamente", HttpStatus.OK);
		
	}
}

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

import com.servicio.exchange.dto.AuthReqDTO;
import com.servicio.exchange.entity.Rol;
import com.servicio.exchange.entity.Usuario;
import com.servicio.exchange.repository.RolRepository;
import com.servicio.exchange.repository.UsuarioRepository;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RolRepository rolRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@PostMapping(value = "/iniciarSesion")
	public ResponseEntity<String> iniciarSesion(@RequestBody AuthReqDTO requestDTO){		
			String username = requestDTO.getUsername();
			String password = requestDTO.getPassword();
			
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			return ResponseEntity.ok("Sesion iniciada correctamente");		
	}
	
	@PostMapping(value="/registrar")
	public ResponseEntity<String> registrar(@RequestBody Usuario usuarioReq){
		
		if(usuarioRepository.existsByUsername(usuarioReq.getUsername())) {
			return ResponseEntity.ok("Error de Usuario");
		}		
		if(usuarioRepository.existsByEmail(usuarioReq.getEmail())) {
			return new ResponseEntity<>("Error de correo",HttpStatus.BAD_REQUEST);
		}		
		Usuario usuario = new Usuario();
		usuario.setName(usuarioReq.getName());
		usuario.setEmail(usuarioReq.getEmail());
		usuario.setUsername(usuarioReq.getUsername());
		usuario.setPassword(passwordEncoder.encode(usuarioReq.getPassword()));		
		Rol rol = rolRepository.findByNombre("ROLE_ADMIN").get();
		usuario.setRoles(Collections.singleton(rol));
		
		usuarioRepository.save(usuario);
		
		return new ResponseEntity<String>("Usuario Registrado Correctamente", HttpStatus.OK);
	}

}

package com.servicio.exchange.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.servicio.exchange.configuracion.SecurityConfig;
import com.servicio.exchange.dto.JwtResponseDTO;
import com.servicio.exchange.dto.LoginDTO;
import com.servicio.exchange.dto.RegistroDTO;
import com.servicio.exchange.entity.Rol;
import com.servicio.exchange.entity.Usuario;
import com.servicio.exchange.seguridad.CustomUserDetailsService;
import com.servicio.exchange.seguridad.JwtTokenGenerator;
import com.servicio.exchange.service.RolService;
import com.servicio.exchange.service.UsuarioService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
	
	
	@Mock
	public AuthenticationManager authenticationManager;
	
	@Mock
	public JwtTokenGenerator jwtTokenGenerator;
	
	@Mock
	public Authentication authentication;
	
	@Mock
	private UsuarioService usuarioService;
	
	@Mock
	private RolService rolService;
	
	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	AuthController authController;

	

	@Test
	void testIniciarSesion_01() {
		
		// Configurar el resultado esperado para el método de autenticación
//		Authentication authentication = mock(Authentication.class);
		when(authenticationManager.authenticate(ArgumentMatchers.any())).thenReturn(authentication);
		
		// Configurar el resultado esperado para el generador de token JWT
		when(jwtTokenGenerator.generarToken(authentication)).thenReturn("123456789");
		

		
//		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		LoginDTO login = new LoginDTO("username", "password");

		ResponseEntity<JwtResponseDTO> response = authController.iniciarSesion(login);
		
		assertEquals("123456789", response.getBody().getToken());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void testRegistrarUsuarioCorrecto() {
		
		RegistroDTO registroDTO = new RegistroDTO();
		registroDTO.setName("John Doe");
		registroDTO.setUsername("john");		
		registroDTO.setEmail("john@example.com");
		registroDTO.setPassword("password");
		

		Usuario usuario = new Usuario();
		usuario.setName(registroDTO.getName());
		usuario.setUsername(registroDTO.getUsername());
		usuario.setEmail(registroDTO.getEmail());
		usuario.setPassword("encodedPassword");
		
		log.info("test registrar: "+usuario.toString());
		
		when(usuarioService.existsByUsername(usuario.getUsername())).thenReturn(false);
		when(usuarioService.existsByEmail(usuario.getEmail())).thenReturn(false);
		
		Rol rol = new Rol();
		when(rolService.findByName("ROLE_ADMIN")).thenReturn(rol);
		usuario.setRoles(Collections.singleton(rol));
		when(passwordEncoder.encode(registroDTO.getPassword())).thenReturn("encodedPassword");
		
		ResponseEntity<String> response = authController.registrar(registroDTO);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Usuario creado correctamente",response.getBody());
		
		verify(usuarioService, times(1)).save(any());
	}
	
	@Test
	void testRegistrarUsuarioExistente() {
		
		RegistroDTO registroDTO = new RegistroDTO();
		registroDTO.setUsername("jesulin");
		registroDTO.setName("jesus");
		registroDTO.setEmail("jesulin@gmail.com");
		registroDTO.setPassword("123456");
		
		when(usuarioService.existsByUsername("jesulin")).thenReturn(true);
		
		ResponseEntity<String> response = authController.registrar(registroDTO);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
	}

}

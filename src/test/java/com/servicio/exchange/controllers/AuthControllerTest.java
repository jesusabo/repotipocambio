package com.servicio.exchange.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.servicio.exchange.dto.JwtResponseDTO;
import com.servicio.exchange.dto.LoginDTO;
import com.servicio.exchange.dto.RegistroDTO;
import com.servicio.exchange.entity.Rol;
import com.servicio.exchange.entity.Usuario;
import com.servicio.exchange.repository.UsuarioRepository;
import com.servicio.exchange.seguridad.JwtTokenProvider;
import com.servicio.exchange.service.RolService;
import com.servicio.exchange.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

	@Mock
	AuthenticationManager authenticationManager;
	
	@Mock
	JwtTokenProvider jwtTokenProvider;
	
	@Mock
	UsuarioService usuarioService;
	
	@Mock
	UsuarioRepository usuarioRepository;
	
	@Mock
	RolService rolService;
	
	@Mock
	PasswordEncoder passwordEncoder;
	
	@InjectMocks
	AuthController authController;
	
	
	
	@Test
	void test_IniciarSesion() {
		
		LoginDTO login = new LoginDTO();
		login.setUsername("Jesus");
		login.setPassword("123456");
		
		LoginDTO login1 = new LoginDTO();
		login1.setUsername("Paz");
		login1.setPassword("000000");
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
		Authentication authentication1 = new UsernamePasswordAuthenticationToken(login1.getUsername(), login1.getPassword());
		
		when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()))).thenReturn(authentication1);
		when(jwtTokenProvider.generarToken(authentication1)).thenReturn("abcdef");
		
		ResponseEntity<JwtResponseDTO> response = authController.iniciarSesion(login);
		assertEquals("abcdef", response.getBody().getValor());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertThat(response.getBody()).isNotNull();
		
	}
	
	@Test
	void test_RegistrarUsuario_Ok() {
		RegistroDTO registro = new RegistroDTO();
		registro.setName("Jesus");
		registro.setUsername("Jesulin");
		registro.setEmail("jesus@gmail.com");
		registro.setPassword("123456");
		registro.setTipo("admin");
		
		when(usuarioService.existsByUsername(registro.getUsername())).thenReturn(false);
		when(usuarioService.existsByEmail(registro.getEmail())).thenReturn(false);
		
		when(passwordEncoder.encode(registro.getPassword())).thenReturn("abcdef");
		
		Rol rol = new Rol();
		rol.setName("ROLE_ADMIN");
		when(rolService.findByName("ROLE_ADMIN")).thenReturn(rol);
		
		ResponseEntity<String> response = authController.registrar(registro);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Usuario creado correctamente", response.getBody());		
		
	}
	
	@Test
	void test_RegistroUsaurio_UsernameExiste() {
		RegistroDTO registro = new RegistroDTO();
		registro.setUsername("Jesus");
		
		when(usuarioService.existsByUsername(registro.getUsername())).thenReturn(true);
		
		ResponseEntity<String> response = authController.registrar(registro);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	void test_RegistroUsuario_EmailExiste() {
		RegistroDTO registro = new RegistroDTO();
		registro.setUsername("Jesus");
		
		when(usuarioService.existsByUsername(registro.getUsername())).thenReturn(true);
		
		ResponseEntity<String> response = authController.registrar(registro);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
		verify(usuarioService, never()).save(any(Usuario.class));
		
//		assertThrows(ResourceNotFoundExceptions.class, ()->{
//			usuarioService.existsByUsername(registro.getUsername());
//		});		
		
	}
}

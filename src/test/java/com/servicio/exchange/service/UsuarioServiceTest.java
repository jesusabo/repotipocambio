package com.servicio.exchange.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.servicio.exchange.entity.Rol;
import com.servicio.exchange.entity.Usuario;
import com.servicio.exchange.exceptions.ResourceNotFoundExceptions;
import com.servicio.exchange.repository.UsuarioRepository;
import com.servicio.exchange.serviceImpl.UsuarioServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class UsuarioServiceTest {
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@InjectMocks
	private UsuarioServiceImpl usuarioServiceImpl;

	
	private Usuario usuario;
	
	@BeforeEach
	void setup() {
		log.info("setup Usuario");
		usuario = new Usuario();
		usuario.setName("jesus");
		usuario.setUsername("jesulin");
		usuario.setEmail("jesulin@gmail.com");
		usuario.setPassword("123456");
	}
	
	
	@Test
	void guardarUsuario() {
		
		when(usuarioRepository.save(usuario)).thenReturn(usuario);
		
		Usuario usuarioGuardado = usuarioServiceImpl.save(usuario);
		
//		assertThrows(ResourceNotFoundExceptions.class, ()->{
//			usuarioServiceImpl.save(usuario);
//		});
//		
		assertThat(usuarioGuardado).isNotNull();
		assertEquals(usuarioGuardado.getId(), null);
		
	}
	
	@Test
	void guardarUsuarioThrowException() {
		
		//when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));
		given(usuarioRepository.findByEmail(usuario.getEmail())).willReturn(Optional.of(null));
		
		assertThrows(ResourceNotFoundExceptions.class, ()->{
			usuarioServiceImpl.save(usuario);
		});
		
		//verify para verificar que el metodo save de usuaurioTepository nunca se llamo durante la prueba para guardar un empleado
		verify(usuarioRepository,never()).save(any(Usuario.class));
		
	}
	
	@Test
	void testListarUsuarios() {
		Usuario user = new Usuario();
		when(usuarioRepository.findAll()).thenReturn(List.of(usuario,user));
		
		List<Usuario> usuarioList = usuarioServiceImpl.findAll();
		
		assertEquals(usuarioList.size(), 2);
		assertEquals(usuarioList.get(0).getName(), "jesus");
				
	}
	
	@Test
	void testEliminarUsuarioByUsernameThrowException() {
		
		when(usuarioRepository.findByUsername("jesus")).thenReturn(Optional.ofNullable(null));
		
		assertThrows(ResourceNotFoundExceptions.class, ()->{
			usuarioServiceImpl.deleteByUsername("jesus1");
		});
		
		verify(usuarioRepository, never()).delete(any(Usuario.class));
		
	}
	
	@Test
	void testEliminarUsuario() {
		Usuario usuario1 = new Usuario();
		usuario1.setUsername("jesus");
		
		
		when(usuarioRepository.findByUsername("jesus")).thenReturn(Optional.of(usuario));
		
		usuarioServiceImpl.deleteByUsername("jesus");
		
		verify(usuarioRepository, times(1)).delete(usuario);
	}

}

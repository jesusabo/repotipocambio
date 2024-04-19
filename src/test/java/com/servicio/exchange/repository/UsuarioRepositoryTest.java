package com.servicio.exchange.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.servicio.exchange.entity.Usuario;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@DisplayName("Guardar Usuario")
	@Test
	void guardarUsuario() {
	
		Usuario usuario = new Usuario();
		usuario.setName("jesus");
		usuario.setUsername("jesulin");
		log.info("<<< : "+usuario.getId());
		Usuario usuarioGuardado = usuarioRepository.save(usuario);
		log.info(">>> : "+usuario.getId());
		assertThat(usuarioGuardado).isNotNull();
		assertThat(usuarioGuardado.getId()).isGreaterThan(0);
		assertEquals(usuarioGuardado.getName(), "jesus");		
		
	}
	
	@DisplayName("Buscar Usuario por Id")
	@Test
	void obtenerUsuarioPorId() {
		
		Usuario usuario = new Usuario();
		usuario.setName("jesus");
		usuario.setUsername("jesulin");
		usuarioRepository.save(usuario);
		
		Usuario usaurioObtenido = usuarioRepository.findById(usuario.getId()).get();
		
		assertThat(usaurioObtenido).isNotNull();
		
	}
	
	@Test
	void obtenerUsurioPorNombre() {
		Usuario usuario = new Usuario();
		usuario.setName("jesus");
		usuario.setUsername("jesulin");
		usuarioRepository.save(usuario);
		
		Usuario usuarioObtenido = usuarioRepository.findByName(usuario.getName()).get();
		
		assertThat(usuarioObtenido).isNotNull();
		assertEquals(usuarioObtenido.getUsername(), "jesuli");
		
	}

}

package com.servicio.exchange.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.servicio.exchange.entity.Rol;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class RolRepositoryTest {

	@Autowired
	private RolRepository rolRepository;

	@DisplayName("Test para guardar un empleado - Not Null")
	@Test
	void guardarRolNotNull() {
		
		Rol rol = new Rol();
		rol.setName("ROLE_ADMIN");
		
		Rol rolInsertado = rolRepository.save(rol);
		
		assertThat(rolInsertado).isNotNull();
		assertThat(rolInsertado.getId()).isGreaterThan(0);
	}
	
	@DisplayName("Test para guardar un empleado - Null")
	@Test
	void guardarRolNull() {
		
		Rol rol = new Rol();
		rol.setName(null);
		
		Rol rolInsertado = rolRepository.save(rol);
		
		assertThat(rolInsertado.getName()).isNull();
//		assertThat(rolInsertado.getId()).isGreaterThan(0);
	}

}

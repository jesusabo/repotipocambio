package com.servicio.exchange.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servicio.exchange.entity.Rol;
import com.servicio.exchange.repository.RolRepository;

@RestController
@RequestMapping("/rol")
public class RolController {
	
	@Autowired
	private RolRepository rolRepository;
	
	
	@PostMapping("/guardar/{tipo}")
	public ResponseEntity<String> guardarRol(@PathVariable String tipo){
		Rol rol = new Rol();
		rol.setName(tipo);
		try {
			rolRepository.save(rol);
			return new ResponseEntity<String>("Rol guardado satisfactoriamente", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Error al guardar Rol", HttpStatus.BAD_REQUEST);
		}
		
	}

}

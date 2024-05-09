package com.servicio.exchange.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicio.exchange.dto.RolesDTO;
import com.servicio.exchange.entity.Rol;
import com.servicio.exchange.service.RolService;

@RestController
@RequestMapping("/rol")
public class RolController {
	
	@Autowired
	private RolService rolService;
	
	@GetMapping("/guardar/{rol}")
	public ResponseEntity<String> guardarRol(@PathVariable String rol){
		Rol rolNuevo = new Rol();
		rolNuevo.setName(rol);
		rolService.save(rolNuevo);
		
		return new ResponseEntity<String>("Rol guardado correctamente", HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public ResponseEntity<RolesDTO> listarRoles(){
		RolesDTO rolesDTO = new RolesDTO();
		rolesDTO.setRoles(rolService.listAll());
		
		return new ResponseEntity<RolesDTO>(rolesDTO, HttpStatus.OK);
		
	}

}

package com.servicio.exchange.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.exchange.entity.Rol;
import com.servicio.exchange.exceptions.ResourceNotFoundException;
import com.servicio.exchange.repository.RolRepository;
import com.servicio.exchange.service.RolService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RolServiceImpl implements RolService{

	@Autowired
	private RolRepository rolRepository;
	
	@Override
	public Rol findByName(String name) {
	
		Optional<Rol> rolOptional = rolRepository.findByName(name);
		
		if(rolOptional.isPresent()) {
			log.info("[[ Recurso Rol Encontrado");
			return rolOptional.get();
		}
		log.info("[[ Recurso Rol NO Encontrado");
		throw new ResourceNotFoundException("rol");
	}

}

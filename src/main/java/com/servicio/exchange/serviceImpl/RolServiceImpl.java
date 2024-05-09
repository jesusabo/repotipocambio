package com.servicio.exchange.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.exchange.entity.Rol;
import com.servicio.exchange.exceptions.ResourceNotFoundExceptions;
import com.servicio.exchange.repository.RolRepository;
import com.servicio.exchange.service.RolService;

@Service
public class RolServiceImpl implements RolService{
	
	@Autowired
	private RolRepository rolRepository;

	@Override
	public Rol findByName(String name) {
		return rolRepository.findByName(name).orElseThrow(()->new ResourceNotFoundExceptions("Rol","name", name));
	}

	@Override
	public Rol save(Rol rol) {
		return rolRepository.save(rol);
	}

	@Override
	public List<Rol> listAll() {
		return rolRepository.findAll();
	}
	
	

}

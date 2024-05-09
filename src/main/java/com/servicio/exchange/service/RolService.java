package com.servicio.exchange.service;

import java.util.List;

import com.servicio.exchange.entity.Rol;

public interface RolService {
	
	Rol findByName(String name);
	Rol save(Rol rol);
	List<Rol> listAll();

}

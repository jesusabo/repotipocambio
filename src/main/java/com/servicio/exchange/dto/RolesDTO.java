package com.servicio.exchange.dto;

import java.util.List;

import com.servicio.exchange.entity.Rol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolesDTO {
	
	private List<Rol> roles;
	
	
}

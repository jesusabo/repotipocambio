package com.servicio.exchange.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicio.exchange.serviceImpl.PokemonImpl;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {
	
	@Autowired
	private PokemonImpl pokemonImpl;
	
	@GetMapping("/{id}")
	public String obtenerPokemon(@PathVariable int id) {
		return pokemonImpl.obtenerPokemon(id);
	}
}

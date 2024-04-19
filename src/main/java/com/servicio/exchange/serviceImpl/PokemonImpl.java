package com.servicio.exchange.serviceImpl;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PokemonImpl {
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	public String obtenerPokemon(int id) {
		String nombre=null;
		String url = null;
		ResponseEntity<Map> response = restTemplate.getForEntity("https://pokeapi.co/api/v2/pokemon/1", Map.class); 
		if(response.getStatusCode().is2xxSuccessful()) {
			
			JSONObject object = new JSONObject(response.getBody());
			log.info(">>>: "+object.toString());
			//nombre=(String)object.get("name");
			nombre = (String)((JSONObject)object.get("sprites")).get("front_default"); 
			//pokemon.sprites.front_default
		}
		return nombre;
	}

}

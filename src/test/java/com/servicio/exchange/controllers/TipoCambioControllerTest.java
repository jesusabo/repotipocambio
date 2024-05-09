package com.servicio.exchange.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicio.exchange.dto.RequestDTO;
import com.servicio.exchange.dto.ResponseDTO;
import com.servicio.exchange.entity.TipoCambio;
import com.servicio.exchange.service.TipoCambioService;


//@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class TipoCambioControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Mock
	TipoCambioService tipoCambioService;
	
	@InjectMocks
	public TipoCambioController tipoCambioController;
	
	@BeforeEach
    void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(tipoCambioController).build();
		objectMapper = new ObjectMapper();
//		MockitoAnnotations.openMocks(this);
    }
	
	
	@Test
	void test_cambioMoneda() {
		
		String from = "SOL";
		String to = "EUR";
		double cantidad = 10;
		
		TipoCambio tc = new TipoCambio();
		tc.setFrom("SOL");
		tc.setTo("EUR");
		tc.setMultiploCambio(0.5);
		
		when(tipoCambioService.findByFromAndTo(from, to)).thenReturn(tc);		
		
		ResponseEntity<ResponseDTO> response = tipoCambioController.cambioMoneda(from, to, cantidad);
		
		assertEquals(5, response.getBody().getMontoAlCambio());
		
	}
	
	@Test
	void test_modificarTipoCambio() throws Exception {
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setMonedaOrigen("SOL");
		requestDTO.setMonedaDestino("EUR");
		requestDTO.setTipoCambioNuevo(0.4);
		
		TipoCambio tipoCambio = new TipoCambio();
		tipoCambio.setFrom(requestDTO.getMonedaOrigen());
		tipoCambio.setTo(requestDTO.getMonedaDestino());
		tipoCambio.setMultiploCambio(0.2);
		
		when(tipoCambioService.findByFromAndTo(requestDTO.getMonedaOrigen(), requestDTO.getMonedaDestino()))
			.thenReturn(tipoCambio);
		
		
		when(tipoCambioService.save(tipoCambio)).thenReturn(tipoCambio);
		
		ResultActions response = mockMvc.perform(post("/cambio/nuevo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDTO)));
		
		response.andExpect(status().isOk())
			.andDo(print())			
			.andExpect(MockMvcResultMatchers.jsonPath("$.nuevoTipoCambio").value(0.4))
			.andExpect(MockMvcResultMatchers.jsonPath("$.tipoCambio").value(0.2));
		}
		
		
//		ResponseEntity<ResponseDTO> response = tipoCambioController.modificarTipoCambio(requestDTO);
//		
//		assertEquals(0.4, response.getBody().getNuevoTipoCambio());
		
	

}

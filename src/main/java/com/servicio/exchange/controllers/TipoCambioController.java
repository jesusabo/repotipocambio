package com.servicio.exchange.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicio.exchange.dto.RequestDTO;
import com.servicio.exchange.dto.ResponseDTO;
import com.servicio.exchange.entity.TipoCambio;
import com.servicio.exchange.service.TipoCambioService;

@RestController
@RequestMapping("/cambio")
public class TipoCambioController {

	private static final Logger log = LoggerFactory.getLogger(TipoCambioController.class);

	@Autowired
	private TipoCambioService tipoCambioService;

	@GetMapping("/{from}/to/{to}/cantidad/{cantidad}")
	public ResponseEntity<ResponseDTO> cambioMoneda(@PathVariable String from, @PathVariable String to,
			@PathVariable double cantidad) {

		log.info("Cambio de Moneda Origen: [" + from + "] a Moneda Destino: [" + to + "]");

		String monedaOrigen = from;
		String monedaDestino = to;
		Double monto = cantidad;

		TipoCambio tc = tipoCambioService.findByFromAndTo(monedaOrigen, monedaDestino);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMonedaOrigen(monedaOrigen);
		responseDTO.setMonedaDestino(monedaDestino);
		responseDTO.setMontoOrigen(monto);
		responseDTO.setMontoAlCambio(tc.getMultiploCambio() * monto);
		responseDTO.setTipoCambio(tc.getMultiploCambio());

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PostMapping("/nuevo")
	public ResponseEntity<ResponseDTO> modificarTipoCambio(@RequestBody RequestDTO request) {

		String monedaOrigen = request.getMonedaOrigen();
		String monedaDestino = request.getMonedaDestino();
		Double nuevoTipoCambio = request.getTipoCambioNuevo();

		log.info("request: "+request.toString());
		
		TipoCambio tipoCambio = tipoCambioService.findByFromAndTo(monedaOrigen, monedaDestino);
		double tipoCambioOld = tipoCambio.getMultiploCambio();
		log.info("tipoCambio: "+tipoCambio.toString());
		
		ResponseDTO responseDTO = new ResponseDTO();

		if (tipoCambio != null) {
			
			tipoCambio.setMultiploCambio(nuevoTipoCambio);
			tipoCambioService.save(tipoCambio);
			
			log.info("tipoCambio: "+tipoCambio.toString());

			

			responseDTO = new ResponseDTO();
			responseDTO.setMonedaOrigen(tipoCambio.getFrom());
			responseDTO.setMonedaDestino(tipoCambio.getTo());
			responseDTO.setTipoCambio(tipoCambioOld);
			responseDTO.setNuevoTipoCambio(tipoCambio.getMultiploCambio());
		}
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/guardar")
	public ResponseEntity<?> guardarTipoCambio(@RequestBody RequestDTO request) {
		log.info("Nuevo Tipo Cambio -> Moneda Origen ["+request.getMonedaOrigen()+"] - Moneda Destino ["+request.getMonedaDestino()+"] - Tipo Cambio ["+request.getTipoCambioNuevo()+"]");
		String monedaOrigen = request.getMonedaOrigen();
		String monedaDestino = request.getMonedaDestino();
		Double tipoCambio = request.getTipoCambioNuevo();
		
		TipoCambio tp = new TipoCambio();
		tp.setFrom(monedaOrigen);
		tp.setTo(monedaDestino);
		tp.setMultiploCambio(tipoCambio);

		TipoCambio nuevoTC = tipoCambioService.save(tp);
		
		ResponseDTO responseDTO =  new ResponseDTO();

		responseDTO.setMonedaOrigen(nuevoTC.getFrom());
		responseDTO.setMonedaDestino(nuevoTC.getTo());
		responseDTO.setTipoCambio(nuevoTC.getMultiploCambio());

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
	
	
	@GetMapping("/listar")
	public ResponseEntity<?> listarTodos(){
		
		return new ResponseEntity<>(tipoCambioService.findAll(), HttpStatus.OK);
	}

}

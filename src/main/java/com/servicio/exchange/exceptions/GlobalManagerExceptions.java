package com.servicio.exchange.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalManagerExceptions extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(ResourceNotFoundExceptions.class)
	public ResponseEntity<?> errorRecursoNoEncontrado(ResourceNotFoundExceptions exception, WebRequest request){
		
		Map<String, Object> map = new HashMap<>();
		map.put("Fecha", new Date());
		map.put("Mensaje", exception.getMessage());
		map.put("Descripcion", request.getDescription(false));
		
		return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Map<String, String> mapErrores = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String nombreCampo = ((FieldError)error).getField();
			String mensaje = error.getDefaultMessage();
			mapErrores.put(nombreCampo, mensaje);
		});
		
		return new ResponseEntity<Object>(mapErrores,HttpStatus.BAD_REQUEST);
	}

}

package com.servicio.exchange.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> manejarException(ResourceNotFoundException exception, WebRequest Webrequest){
		
		Map<String, Object> map = new HashMap<>();
		map.put("fecha", new Date());
		map.put("Exception", exception.getMessage());
		map.put("Description", Webrequest.getDescription(false));
		
		return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
	}

}

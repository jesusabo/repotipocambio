package com.servicio.exchange.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalManagerExceptions extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(ResourceNotFoundExceptions.class)
	public ResponseEntity<?> recursoNoEncontrado(ResourceNotFoundExceptions exception, WebRequest request){
		
		Map<String, Object> map = new HashMap<>();
		map.put("Fecha", new Date());
		map.put("Mensaje", exception.getMessage());
		map.put("Path", request.getDescription(false));
		
		return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> errorProhibido(AccessDeniedException exception, WebRequest request){
		Map<String, Object> map = new HashMap<>();
		map.put("Fecha",  new Date());
		map.put("Path", request.getDescription(false));
		return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
	}
	
//	@ExceptionHandler(AuthenticationException.class)
//	public ResponseEntity<?> errorSinAutenticar(AuthenticationException exception, WebRequest request){
//		Map<String, Object> map = new HashMap<>();
//		map.put("Fecha",  new Date());
//		map.put("Description", request.getDescription(false));
//		return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
//	}
	
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> errorProhibido(DataIntegrityViolationException exception, WebRequest request){
		Map<String, Object> map = new HashMap<>();
		map.put("Fecha",  new Date());
		map.put("Path", request.getDescription(false));
		map.put("Mensaje", "El rol ya se encuentra registrado");
		return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> mapa = new HashMap<>();
		ex.getBindingResult().getAllErrors().stream().forEach(error ->{
			String campo = ((FieldError)error).getField();
			String mensaje = error.getDefaultMessage();
			
			mapa.put(campo, mensaje);
		});
		return new ResponseEntity<Object>(mapa, HttpStatus.BAD_REQUEST);
	}

}

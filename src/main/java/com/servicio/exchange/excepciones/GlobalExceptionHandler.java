package com.servicio.exchange.excepciones;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(CambioNotFoundException.class)
	public ResponseEntity<Object> manejarRecursoNoEncontrado(CambioNotFoundException exception, WebRequest webRequest){
		Map<String,Object> map = new HashMap<>();
		map.put("Fecha", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS").format(new Date()));
//		map.put("Fecha", new Date());
		map.put("Exception", exception.getMessage());
		map.put("Description", webRequest.getDescription(false));
		map.put("Codigo de Error", "ABC01");
		return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
			Map<String, String> errores = new HashMap<>();
//			List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
			
//			StringBuilder errorMessage = new StringBuilder();
//			
//			for(FieldError fe: fieldErrors) {
//				errorMessage.append(fe.getDefaultMessage()).append("; ");
//			}
			
			ex.getBindingResult().getAllErrors().forEach(error ->{
			String nombreCampo = ((FieldError)error).getField();
			String mensaje = error.getDefaultMessage();
			
			errores.put(nombreCampo, mensaje);
		});
		
		
		return new ResponseEntity<>(errores,HttpStatus.BAD_REQUEST);
	}
	
}
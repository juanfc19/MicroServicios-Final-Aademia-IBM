package com.proyecto.perfiles.tarjetas.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(EmptyException.class)
	public ResponseEntity<Object> handleExceptions(EmptyException exception){
		Response response = new Response();
		response.setStatus(HttpStatus.BAD_REQUEST);
		response.setMessage("Existen campos vacios");
		response.setDateTime(LocalDateTime.now());
		response.setData(null);
		ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		return entity;
	}
	
	@ExceptionHandler(EdadInvalidaException.class)
	public ResponseEntity<Object> handleExceptions(EdadInvalidaException exception){
		Response response = new Response();
		response.setStatus(HttpStatus.CONFLICT);
		response.setMessage("La edad minima para obtener una tarjeta de credito es de 18 años");
		response.setDateTime(LocalDateTime.now());
		response.setData(null);
		ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.CONFLICT);
		return entity;
	}
	
	@ExceptionHandler(PerfilNotFound.class)
	public ResponseEntity<Object> handleExceptions(PerfilNotFound exception){
		Response response = new Response();
		response.setStatus(HttpStatus.NOT_FOUND);
		response.setMessage("No se encontraron tarjetas de credito que cumplan con tu perfil");
		response.setDateTime(LocalDateTime.now());
		response.setData(null);
		ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		return entity;
	}
	
	@ExceptionHandler(SalarioInvalidoException.class)
	public ResponseEntity<Object> handleExceptions(SalarioInvalidoException exception){
		Response response = new Response();
		response.setStatus(HttpStatus.CONFLICT);
		response.setMessage("El monto del salario minimo para obtener una tarjeta de credito es de $7000.00 m/n");
		response.setDateTime(LocalDateTime.now());
		response.setData(null);
		ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.CONFLICT);
		return entity;
	}
}

package com.proyecto.sucursales.exceptions;

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
		response.setMessage("Existen campos vacios, por favor verifica que todos los campos esten completos");
		response.setDateTime(LocalDateTime.now());
		response.setData(null);
		ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		return entity;
	}
	
	@ExceptionHandler(FormatInvalidCpException.class)
	public ResponseEntity<Object> handleExceptions(FormatInvalidCpException exception){
		Response response = new Response();
		response.setStatus(HttpStatus.BAD_REQUEST);
		response.setMessage("El formato del Código Postal es invalido");
		response.setDateTime(LocalDateTime.now());
		response.setData(null);
		ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		return entity;
	}
	
	@ExceptionHandler(SucursalesNotFound.class)
	public ResponseEntity<Object> handleExceptions(SucursalesNotFound exception){
		Response response = new Response();
		response.setStatus(HttpStatus.NOT_FOUND);
		response.setMessage("No se encontraron Sucursales");
		response.setDateTime(LocalDateTime.now());
		response.setData(null);
		ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		return entity;
	}
}

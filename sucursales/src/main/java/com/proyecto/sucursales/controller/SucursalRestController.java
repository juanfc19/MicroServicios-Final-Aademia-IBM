package com.proyecto.sucursales.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.sucursales.exceptions.EmptyException;
import com.proyecto.sucursales.exceptions.FormatInvalidCpException;
import com.proyecto.sucursales.exceptions.Response;
import com.proyecto.sucursales.exceptions.SucursalesNotFound;
import com.proyecto.sucursales.mapping.Mapping;
import com.proyecto.sucursales.modelo.GPS;
import com.proyecto.sucursales.modelo.Sucursal;
import com.proyecto.sucursales.modelo.SucursalRequestBody;
import com.proyecto.sucursales.service.ISucursalService;

@RestController
@RequestMapping(Mapping.SUCURSAL)
public class SucursalRestController {
	@Autowired
	ISucursalService sucursalService;
	
	@PostMapping("/buscar-estado-colonia")
	public ResponseEntity<Object> buscarSucursalEstadoColonia(@RequestBody SucursalRequestBody sucRequestBody){
		String estado = sucRequestBody.getEstado();
		String colonia = sucRequestBody.getColonia();
				
		List<Sucursal> sucursales;

		if(estado.isEmpty()) {
			throw new EmptyException();
		}else if(colonia.isEmpty() || colonia.isBlank()) {
			sucursales = sucursalService.buscarPorEstado(estado);
		}else{
			sucursales = sucursalService.buscarPorEstadoColonia(estado, colonia);
		}
		
		if(sucursales.size() == 0) {
			throw new SucursalesNotFound();
		}else {
			Response response = new Response();
			response.setStatus(HttpStatus.OK);
			response.setMessage("Lista de Sucursales");
			response.setDateTime(LocalDateTime.now());
			response.setData(sucursales);
			ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.OK);
			return entity;	
		}	
	}
	
	@GetMapping(value = "/buscar-cp", params="cp")
	public ResponseEntity<Object> buscarSucursalCp(@RequestParam String cp){
		if(cp.isEmpty() || cp.isBlank())
			throw new EmptyException();
		
		if(cp.length() < 5 || cp.length() > 5)
			throw new FormatInvalidCpException();
		
		List<Sucursal> sucursales;
		
		sucursales = sucursalService.buscarPorCp(cp);
		
		if(sucursales.size() == 0) {
			throw new SucursalesNotFound();
		}else {
			Response response = new Response();
			response.setStatus(HttpStatus.OK);
			response.setMessage("Lista de Sucursales");
			response.setDateTime(LocalDateTime.now());
			response.setData(sucursales);
			ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.OK);
			return entity;	
		}
	}
	
	@PostMapping(value = "/buscar-gps")
	public ResponseEntity<Object> buscarSucursalGps(@RequestBody GPS gps){
		String lat = gps.getLat();
		String lng = gps.getLng();
		
		if(lat.isEmpty() || lat.isBlank() || lng.isEmpty() || lng.isBlank())
			throw new EmptyException();
		
		List<Sucursal> sucursales;
		
		sucursales = sucursalService.buscarPorGPS(Double.parseDouble(gps.getLat()), Double.parseDouble(gps.getLng()));
		
		if(sucursales.size() == 0) {
			throw new SucursalesNotFound();
		}else {
			Response response = new Response();
			response.setStatus(HttpStatus.OK);
			response.setMessage("Lista de Sucursales");
			response.setDateTime(LocalDateTime.now());
			response.setData(sucursales);
			ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.OK);
			return entity;	
		}
	}
}

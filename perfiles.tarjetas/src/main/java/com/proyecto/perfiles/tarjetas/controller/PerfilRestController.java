package com.proyecto.perfiles.tarjetas.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.perfiles.tarjetas.exception.EdadInvalidaException;
import com.proyecto.perfiles.tarjetas.exception.EmptyException;
import com.proyecto.perfiles.tarjetas.exception.PerfilNotFound;
import com.proyecto.perfiles.tarjetas.exception.Response;
import com.proyecto.perfiles.tarjetas.exception.SalarioInvalidoException;
import com.proyecto.perfiles.tarjetas.mapping.Mapping;
import com.proyecto.perfiles.tarjetas.models.Perfil;
import com.proyecto.perfiles.tarjetas.models.PerfilRequest;
import com.proyecto.perfiles.tarjetas.service.IPerfilService;

@RestController
@RequestMapping(Mapping.API)
public class PerfilRestController {
	
	@Autowired
	IPerfilService perfilService;
	
	@PostMapping("/obtener-perfil")
	public ResponseEntity<Object> getPerfil(@RequestBody PerfilRequest perfilRequest) {
		if(perfilRequest.getPasion().isEmpty() || perfilRequest.getPasion().isBlank())
			throw new EmptyException();
		
		if(perfilRequest.getSalario() < 7000)
			throw new SalarioInvalidoException();
		
		if(perfilRequest.getEdad() < 18)
			throw new EdadInvalidaException();
		
		Perfil perfil = perfilService.getPerfil(perfilRequest);
		
		if(perfil.getTarjetas().size() == 0) {
			throw new PerfilNotFound();
		}else {
			Response response = new Response();
			response.setStatus(HttpStatus.OK);
			response.setMessage("Perfil de Tarjetas de Credito");
			response.setDateTime(LocalDateTime.now());
			response.setData(perfil);
			ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.OK);
			return entity;	
		}
	}
	

}

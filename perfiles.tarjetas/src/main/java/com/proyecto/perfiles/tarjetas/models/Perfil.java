package com.proyecto.perfiles.tarjetas.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Perfil {
	
	private String pasion;
	private double salario;
	private int edad;
	private List<String> tarjetas;

}

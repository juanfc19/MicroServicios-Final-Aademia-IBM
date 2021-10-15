package com.proyecto.perfiles.tarjetas.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PerfilRequest {
	private String pasion;
	private double salario;
	private int edad;
}

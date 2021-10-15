package com.proyecto.sucursales.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SucursalRequestBody {
	private String estado;
	private String colonia;
}

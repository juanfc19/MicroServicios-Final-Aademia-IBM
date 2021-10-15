package com.proyecto.sucursales.modelo;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Sucursal {
	private String idSucursal;
	private String estado;
	private String calle;
	private String colonia;
	private String codigoPostal;
	private String direccionCompleta;
	private String latitud;
	private String longitud;
	private String horario;
	private String tipoSucursal;
	
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        Sucursal suc = (Sucursal) obj;
        return Objects.equals(idSucursal, suc.idSucursal);
    }
}

package com.proyecto.sucursales.service;

import java.util.List;

import com.proyecto.sucursales.modelo.Sucursal;

public interface ISucursalService {
	public List<Sucursal> buscarPorEstado(String ciudad);
	public List<Sucursal> buscarPorEstadoColonia(String estado, String colonia);
	public List<Sucursal> buscarPorCp(String cp);
	public List<Sucursal> buscarPorGPS(double lat, double lng);
}

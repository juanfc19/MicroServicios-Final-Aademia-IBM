package com.proyecto.sucursales.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.proyecto.sucursales.feign.ISucursalFeign;
import com.proyecto.sucursales.modelo.Sucursal;

@Service
public class SucursalServiceImpl implements ISucursalService{
	@Autowired
	ISucursalFeign sucursalFeign;
	
	private List<Sucursal> sucursalesBusqueda = new LinkedList<Sucursal>();
	private List<Sucursal> sucursales = new LinkedList<Sucursal>();
	
	@Override
	public List<Sucursal> buscarPorEstado(String estado) {
		sucursalesBusqueda.clear();
		sucursales = getSucursales();
		sucursales.stream()
				.filter((Sucursal sucursal)-> sucursal.getEstado().equals(estado))
				.forEach((Sucursal sucursal)->{
					if(!sucursalesBusqueda.contains(sucursal)) {
						sucursalesBusqueda.add(sucursal);
					}
				});
		return sucursalesBusqueda;
	}
	
	@Override
	public List<Sucursal> buscarPorEstadoColonia(String estado, String colonia) {
		sucursalesBusqueda.clear();
		sucursales = getSucursales();
		sucursales.stream()
				.filter((Sucursal sucursal)-> sucursal.getEstado().equals(estado) && sucursal.getColonia().equals(colonia))
				.forEach((Sucursal sucursal)->{
					if(!sucursalesBusqueda.contains(sucursal)) {
						sucursalesBusqueda.add(sucursal);
					}
				});
		return sucursalesBusqueda;
	}
	
	@Override
	public List<Sucursal> buscarPorCp(String cp) {
		sucursalesBusqueda.clear();
		sucursales = getSucursales();
		sucursales.stream()
		.filter((Sucursal sucursal)-> sucursal.getCodigoPostal().substring(0, 3).equals(cp.substring(0, 3)))
		.forEach((Sucursal sucursal)->{
			if(!sucursalesBusqueda.contains(sucursal)) {
				sucursalesBusqueda.add(sucursal);
			}
		});
		return sucursalesBusqueda;
	}

	@Override
	public List<Sucursal> buscarPorGPS(double lat, double lng) {
		sucursalesBusqueda.clear();
		double radioBusqueda = 2; //en km
		sucursales = getSucursales();
		sucursales
		.forEach((Sucursal sucursal)->{
			double distanciaSucursal = distanciaCoord(lat, lng, Double.parseDouble(sucursal.getLatitud()), Double.parseDouble(sucursal.getLongitud()));
			if(distanciaSucursal <= radioBusqueda && !sucursalesBusqueda.contains(sucursal)) {
				sucursalesBusqueda.add(sucursal);
			}
		});
		return sucursalesBusqueda;
	}
	
	public List<Sucursal> getSucursales(){
		String respuesta = sucursalFeign.getSucurales();
		String json = respuesta.substring(13, respuesta.length()-2);
		List<Sucursal> sucursalesList = new LinkedList<Sucursal>();
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
			
			jsonObject = (JSONObject) jsonObject.get("Servicios");
			
			Collection<JSONArray> arraySucursales = new LinkedList<JSONArray>();
			arraySucursales = jsonObject.values();
			
			arraySucursales.forEach((Object object)->{
				Collection<JSONArray> keys = new LinkedList<JSONArray>();
				keys = ((HashMap) object).values();
				keys.forEach((Object key)->{
					Collection<JSONArray> sucursales = new LinkedList<JSONArray>();
					sucursales = ((HashMap) key).values();
					sucursales.forEach((Object sucursal)->{
						Object[] infoSuc = ((ArrayList) sucursal).toArray();
						String[] infoDireccion = infoSuc[4].toString().split(", ");
						if(infoDireccion.length > 3) {
							Sucursal sucursalObj = new Sucursal();
							sucursalObj.setIdSucursal(infoSuc[0].toString());
							sucursalObj.setEstado(infoSuc[17].toString());
							sucursalObj.setCalle(infoSuc[3].toString());
							sucursalObj.setColonia(infoDireccion[0]);
							sucursalObj.setCodigoPostal(infoDireccion[2].substring(4).trim());
							sucursalObj.setDireccionCompleta(infoSuc[4].toString());
							sucursalObj.setLatitud(infoSuc[16].toString());
							sucursalObj.setLongitud(infoSuc[15].toString());
							sucursalObj.setHorario(infoSuc[13].toString()+" A "+infoSuc[14].toString());
							sucursalObj.setTipoSucursal(infoSuc[19].toString());
							sucursalesList.add(sucursalObj);
						}
					});
				});
			});
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sucursalesList;
	}
	
	public static double distanciaCoord(double lat1, double lng1, double lat2, double lng2) {   
        double radioTierra = 6371;//en kilómetros  
        double dLat = Math.toRadians(lat2 - lat1);  
        double dLng = Math.toRadians(lng2 - lng1);  
        double sindLat = Math.sin(dLat / 2);  
        double sindLng = Math.sin(dLng / 2);  
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)  
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));  
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));  
        double distancia = radioTierra * va2;  
   
        return distancia;  
    } 
}

package com.proyecto.sucursales.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "sucursales", url= "${feign.sucursales.uri}")
public interface ISucursalFeign {
		

	@RequestMapping(method = RequestMethod.GET, value = "${feign.sucursales.getSucursales}")
	public String getSucurales();
}

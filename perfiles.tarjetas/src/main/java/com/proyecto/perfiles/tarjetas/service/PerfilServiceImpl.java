package com.proyecto.perfiles.tarjetas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.perfiles.tarjetas.dao.IPerfilDao;
import com.proyecto.perfiles.tarjetas.models.Perfil;
import com.proyecto.perfiles.tarjetas.models.PerfilRequest;

@Service
public class PerfilServiceImpl implements IPerfilService {
	@Autowired
	IPerfilDao perfilDao;

	@Override
	public Perfil getPerfil(PerfilRequest perfilRequest) {
		List<String> tarjetas = perfilDao.getPerfil(perfilRequest);
		Perfil perfil = new Perfil();
		perfil.setEdad(perfilRequest.getEdad());
		perfil.setPasion(perfilRequest.getPasion());
		perfil.setSalario(perfilRequest.getSalario());
		perfil.setTarjetas(tarjetas);
		return perfil;
	}
}

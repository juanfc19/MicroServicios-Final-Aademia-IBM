package com.proyecto.perfiles.tarjetas.dao;

import java.util.List;

import com.proyecto.perfiles.tarjetas.models.Perfil;
import com.proyecto.perfiles.tarjetas.models.PerfilRequest;

public interface IPerfilDao {

	public List<String> getPerfil(PerfilRequest perfilRequest);
}

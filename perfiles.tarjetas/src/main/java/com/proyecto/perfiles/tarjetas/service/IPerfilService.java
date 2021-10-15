package com.proyecto.perfiles.tarjetas.service;

import com.proyecto.perfiles.tarjetas.models.Perfil;
import com.proyecto.perfiles.tarjetas.models.PerfilRequest;

public interface IPerfilService {
	Perfil getPerfil(PerfilRequest perfilRequest);
}

package com.proyecto.perfiles.tarjetas.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proyecto.perfiles.tarjetas.models.Perfil;
import com.proyecto.perfiles.tarjetas.models.PerfilRequest;

@Repository
public class PerfilDaoImpl implements IPerfilDao {
	@Autowired 
	JdbcTemplate template;

	@Override
	public List<String> getPerfil(PerfilRequest perfilRequest) {
		String query = "SELECT nombreTarjeta  FROM Perfiles AS P "
				+ "INNER JOIN Tarjetas AS T ON T.tarjetaId = P.tarjetaId "
				+ "INNER JOIN Salarios  AS Smin ON Smin.salarioId = P.salarioMinId "
				+ "INNER JOIN Salarios AS Smax ON Smax.salarioId = P.salarioMaxId "
				+ "INNER JOIN Pasiones AS Pa ON Pa.pasionId = P.pasionId "
				+ "INNER JOIN Edades AS Emin ON Emin.edadId = P.edadMinId "
				+ "INNER JOIN Edades AS Emax ON Emax.edadId = P.edadMaxId "
				+ "WHERE Emin.edad <= ? AND Emax.edad >= ? AND "
				+ "Smin.monto  <= ? AND (Smax.monto >= ? OR Smax.monto = 0) AND "
				+ "Pa.nombrePasion = ?";
		
		return this.template.queryForList(query, String.class, 
				perfilRequest.getEdad(), 
				perfilRequest.getEdad(), 
				perfilRequest.getSalario(), 
				perfilRequest.getSalario(), 
				perfilRequest.getPasion());
	}

}

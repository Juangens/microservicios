package com.sinensia.ligaGFT.business.services;

import java.util.Map;

import com.sinensia.ligaGFT.business.model.Partido;

public interface ClasificacionService {

	Map<String, Integer> getClasificacion();

	void añadirPartido(Partido partido);
}

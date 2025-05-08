package com.sinensia.ligaGFT.business.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sinensia.ligaGFT.business.model.Equipo;
import com.sinensia.ligaGFT.business.services.EquipoService;
import com.sinensia.ligaGFT.presentation.config.MS1Config;

@Service
public class EquipoServiceImpl implements EquipoService {

	private RestTemplate restTemplate;

	public EquipoServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Equipo getEquipoById(Long id) {
		return restTemplate.getForObject(MS1Config.NODE_URL + "/equipos/{id}", Equipo.class, id);
	}

}

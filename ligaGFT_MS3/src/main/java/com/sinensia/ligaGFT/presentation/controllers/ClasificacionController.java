package com.sinensia.ligaGFT.presentation.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.ligaGFT.business.services.ClasificacionService;

@RestController
@RequestMapping("/clasificacion")
public class ClasificacionController {

	private ClasificacionService clasificacionService;

	public ClasificacionController(ClasificacionService clasificacionService) {
		this.clasificacionService = clasificacionService;
	}

	@GetMapping
	public Map<String, Integer> mostrarClasificacion() {
		return clasificacionService.getClasificacion();
	}

}

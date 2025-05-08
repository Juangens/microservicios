package com.sinensia.ligaGFT.business.model;

public record Partido(Long idPartido, Long idEquipoLocal, Long idVisitance, EstadoPartido estado, int golesLocales,
		int golesVisitanes) {

}

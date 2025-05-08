package com.sinensia.ligaGFT.business.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "idPartido")
public class PartidoMensajeria {

	private Long idPartido;
	private Long idEquipoLocal;
	private Long idVisitance;
	private EstadoPartido estado;
	private int golesLocales;
	private int golesVisitanes;

}

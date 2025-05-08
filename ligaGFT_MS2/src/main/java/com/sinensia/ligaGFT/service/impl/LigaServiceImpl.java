package com.sinensia.ligaGFT.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sinensia.ligaGFT.business.model.EstadoPartido;
import com.sinensia.ligaGFT.business.model.LanceMensajeria;
import com.sinensia.ligaGFT.business.model.PartidoMensajeria;
import com.sinensia.ligaGFT.business.model.TipoLance;
import com.sinensia.ligaGFT.mensajeria.MensajeriaEnviarService;
import com.sinensia.ligaGFT.service.LigaService;

@Service
public class LigaServiceImpl implements LigaService {

	private final Map<Long, PartidoMensajeria> PARTIDOS = new HashMap<Long, PartidoMensajeria>();;

	private final MensajeriaEnviarService mensajeriaEnviarService;

	public LigaServiceImpl(MensajeriaEnviarService mensajeriaService) {
		init();
		this.mensajeriaEnviarService = mensajeriaService;
	}

	@Override
	public void procesarLance(LanceMensajeria lance) {

		if (filtrarTipoLanceInicial(lance.tipo())) {

			PartidoMensajeria partido = PARTIDOS.get(lance.idPartido());

			estadosActualizadosYEnviados(lance, partido);
		}
	}

	public void estadosActualizadosYEnviados(LanceMensajeria lance, PartidoMensajeria partido) {
		if (partido.getEstado() == EstadoPartido.PENDIENTE & lance.tipo() == TipoLance.INICIO) {

			actualizarEstado(partido.getIdPartido(), lance.tipo());
			mensajeriaEnviarService.enviarPartido(partido);
		}

		if (partido.getEstado() != EstadoPartido.FINALIZADO && lance.tipo() != null) {

			if (partido.getEstado() != EstadoPartido.PRIMERA_PARTE & lance.tipo() != TipoLance.INICIO_SEGUNDA_PARTE) {

				actualizarMarcador(lance.idPartido(), lance.tipo());

			}

		}
	}

	@Override
	public void actualizarMarcador(Long idPartido, TipoLance tipoLance) {

		PartidoMensajeria partido = PARTIDOS.get(idPartido);

		switch (tipoLance) {

		case GOL_LOCAL:
			partido.setGolesLocales(partido.getGolesLocales() + 1);
			break;
		case GOL_VISITANTE:
			partido.setGolesVisitanes(partido.getGolesVisitanes() + 1);
			break;
		case GOL_LOCAL_ANULADO:
			partido.setGolesLocales(partido.getGolesLocales() - 1);
			break;
		case GOL_VISITANTE_ANULADO:
			partido.setGolesVisitanes(partido.getGolesVisitanes() - 1);
			break;

		default:
			break;

		}

		PARTIDOS.replace(idPartido, partido);
		procesarResultadoPartido(idPartido);

	}

	@Override
	public void actualizarEstado(Long idPartido, TipoLance tipoLance) {

		PartidoMensajeria partido = PARTIDOS.get(idPartido);

		switch (tipoLance) {

		case INICIO:
			partido.setEstado(EstadoPartido.PRIMERA_PARTE);
			break;

		case FIN_PRIMERA_PARTE:
			partido.setEstado(EstadoPartido.DESCANSO);
			break;

		case INICIO_SEGUNDA_PARTE:
			partido.setEstado(EstadoPartido.SEGUNDA_PARTE);
			break;

		case FIN:
			partido.setEstado(EstadoPartido.FINALIZADO);
			break;

		default:
			throw new IllegalArgumentException("Unexpected value: " + tipoLance);
		}

		PARTIDOS.replace(idPartido, partido);
		procesarResultadoPartido(idPartido);
	}

	private boolean filtrarTipoLanceInicial(TipoLance tipo) {
		if (tipo == TipoLance.INICIO || tipo == TipoLance.FIN_PRIMERA_PARTE || tipo == TipoLance.INICIO_SEGUNDA_PARTE
				|| tipo == TipoLance.GOL_LOCAL || tipo == TipoLance.GOL_LOCAL_ANULADO || tipo == TipoLance.GOL_VISITANTE
				|| tipo == TipoLance.GOL_VISITANTE_ANULADO) {
			return true;
		}
		return false;

	}

	public void init() {

		PARTIDOS.clear();

		PARTIDOS.put(1L, new PartidoMensajeria(1L, 1L, 2L, EstadoPartido.PENDIENTE, 0, 0));
		PARTIDOS.put(2L, new PartidoMensajeria(2L, 3L, 4L, EstadoPartido.PENDIENTE, 0, 0));
		PARTIDOS.put(3L, new PartidoMensajeria(3L, 2L, 3L, EstadoPartido.PENDIENTE, 0, 0));
		PARTIDOS.put(4L, new PartidoMensajeria(4L, 4L, 1L, EstadoPartido.PENDIENTE, 0, 0));
		PARTIDOS.put(5L, new PartidoMensajeria(5L, 1L, 3L, EstadoPartido.PENDIENTE, 0, 0));
		PARTIDOS.put(6L, new PartidoMensajeria(6L, 4L, 2L, EstadoPartido.PENDIENTE, 0, 0));
		PARTIDOS.put(7L, new PartidoMensajeria(7L, 2L, 1L, EstadoPartido.PENDIENTE, 0, 0));
		PARTIDOS.put(8L, new PartidoMensajeria(8L, 4L, 3L, EstadoPartido.PENDIENTE, 0, 0));
		PARTIDOS.put(9L, new PartidoMensajeria(9L, 3L, 2L, EstadoPartido.PENDIENTE, 0, 0));
		PARTIDOS.put(10L, new PartidoMensajeria(10L, 1L, 4L, EstadoPartido.PENDIENTE, 0, 0));
		PARTIDOS.put(11L, new PartidoMensajeria(11L, 3L, 1L, EstadoPartido.PENDIENTE, 0, 0));
		PARTIDOS.put(12L, new PartidoMensajeria(12L, 2L, 4L, EstadoPartido.PENDIENTE, 0, 0));
	}

	private void procesarResultadoPartido(Long idPartido) {
		PartidoMensajeria partido = PARTIDOS.get(idPartido);
		int valorAbsoluto = Math.abs(partido.getGolesLocales() - partido.getGolesVisitanes());
		if (valorAbsoluto == 1 || valorAbsoluto == 0) {
			mensajeriaEnviarService.enviarPartido(partido);
		}
	}

	PartidoMensajeria obtenerPartidoPorId(Long id) {
		return PARTIDOS.get(id);
	}

}

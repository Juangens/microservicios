package com.sinensia.ligaGFT.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sinensia.ligaGFT.business.model.EstadoPartido;
import com.sinensia.ligaGFT.business.model.LanceMensajeria;
import com.sinensia.ligaGFT.business.model.PartidoMensajeria;
import com.sinensia.ligaGFT.business.model.TipoLance;
import com.sinensia.ligaGFT.mensajeria.MensajeriaEnviarService;

class LigaServiceImplTest {

	private MensajeriaEnviarService mensajeriaMock;
	private LigaServiceImpl ligaService;

	@BeforeEach
	void setUp() {
		mensajeriaMock = mock(MensajeriaEnviarService.class);
		ligaService = new LigaServiceImpl(mensajeriaMock);
	}

	@Test
	void testLigaServiceImpl() {
		assertNotNull(ligaService);
	}

	@Test
	void testInit() {
		ligaService.init();
		assertDoesNotThrow(() -> ligaService.init());
	}

	@Test
	void testActualizarEstado() {
		ligaService.actualizarEstado(1L, TipoLance.INICIO);
		PartidoMensajeria partido = ligaService.obtenerPartidoPorId(1L);
		assertEquals(EstadoPartido.PRIMERA_PARTE, partido.getEstado());
	}

	@Test
	void testActualizarMarcador() {
		ligaService.actualizarMarcador(1L, TipoLance.GOL_LOCAL);
		PartidoMensajeria partido = ligaService.obtenerPartidoPorId(1L);
		assertEquals(1, partido.getGolesLocales());
	}

	@Test
	void testProcesarLance() {
		LanceMensajeria lance = new LanceMensajeria(1L, 0, TipoLance.INICIO, null);
		ligaService.procesarLance(lance);
		PartidoMensajeria partido = ligaService.obtenerPartidoPorId(1L);
		assertEquals(EstadoPartido.PRIMERA_PARTE, partido.getEstado());
		verify(mensajeriaMock).enviarPartido(partido);
	}

	@Test
	void testEstadosActualizadosYEnviados() {
		PartidoMensajeria partido = ligaService.obtenerPartidoPorId(2L);
		partido.setEstado(EstadoPartido.PENDIENTE);

		LanceMensajeria lance = new LanceMensajeria(2L, 0, TipoLance.INICIO, null);

		ligaService.estadosActualizadosYEnviados(lance, partido);

		assertEquals(EstadoPartido.PRIMERA_PARTE, partido.getEstado());
		verify(mensajeriaMock).enviarPartido(partido);
	}

}

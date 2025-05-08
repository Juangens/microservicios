package com.sinensia.ligaGFT.presentation.consumers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sinensia.ligaGFT.business.model.EstadoPartido;
import com.sinensia.ligaGFT.business.model.Partido;
import com.sinensia.ligaGFT.business.services.impl.ClasificacionServiceImpl;
import com.sinensia.ligaGFT.business.services.impl.EquipoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ClasificacionConsumerTest {

	@Mock
	private EquipoServiceImpl equipoService;

	private ClasificacionServiceImpl clasificacionServiceImpl;
	private ClasificacionConsumer clasificacionConsumer;

	@BeforeEach
	void init() {
		initObjects();
	}

	@Test
	void testRecibirPartido() {
		clasificacionConsumer.recibirPartido(new Partido(1L, 1L, 2L, EstadoPartido.DESCANSO, 1, 1));

		assertThat(clasificacionServiceImpl.getPartidos().size() == 1);
	}

	private void initObjects() {
		clasificacionServiceImpl = new ClasificacionServiceImpl(equipoService);
		clasificacionConsumer = new ClasificacionConsumer(clasificacionServiceImpl);
	}
}

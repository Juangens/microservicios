package com.sinensia.ligaGFT.business.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.sinensia.ligaGFT.business.model.Equipo;
import com.sinensia.ligaGFT.business.model.EstadoPartido;
import com.sinensia.ligaGFT.business.model.Partido;
import com.sinensia.ligaGFT.business.services.impl.ClasificacionServiceImpl;

@ExtendWith(MockitoExtension.class)
class ClasificacionServiceImplTest {

	private RestTemplate restTemplate = new RestTemplate();

	@Mock
	private EquipoService equipoService;
	private ClasificacionServiceImpl clasificacionServiceImpl;

	private Equipo equipo1;
	private Equipo equipo2;

	@BeforeEach
	void init() {
		initObjects();
	}

	@Test
	void addTest() {
		clasificacionServiceImpl.añadirPartido(new Partido(1L, 1L, 2L, EstadoPartido.DESCANSO, 1, 1));

		assertThat(clasificacionServiceImpl.getPartidos().size() == 1);
	}

	@Test
	void crearClasificacion() {
		clasificacionServiceImpl.añadirPartido(new Partido(1L, 1L, 2L, EstadoPartido.DESCANSO, 1, 1));
		clasificacionServiceImpl.añadirPartido(new Partido(2L, 1L, 2L, EstadoPartido.DESCANSO, 4, 1));
		clasificacionServiceImpl.añadirPartido(new Partido(3L, 1L, 2L, EstadoPartido.DESCANSO, 2, 1));
		clasificacionServiceImpl.añadirPartido(new Partido(3L, 1L, 2L, EstadoPartido.DESCANSO, 2, 1));
		clasificacionServiceImpl.añadirPartido(new Partido(3L, 1L, 2L, EstadoPartido.DESCANSO, 0, 2));

		when(equipoService.getEquipoById(1L)).thenReturn(equipo1);
		when(equipoService.getEquipoById(2L)).thenReturn(equipo2);

		Map<String, Integer> expectedClasificacion = new HashMap<String, Integer>();
		expectedClasificacion.put("FC 1", 10);
		expectedClasificacion.put("FC 2", 4);
		Map<String, Integer> clasificacion = clasificacionServiceImpl.getClasificacion();

		assertMapEquals(expectedClasificacion, clasificacion);
	}

	private void initObjects() {
		clasificacionServiceImpl = new ClasificacionServiceImpl(equipoService);

		equipo1 = new Equipo();
		equipo1.setId(1L);
		equipo1.setNombre("FC 1");

		equipo2 = new Equipo();
		equipo2.setId(2L);
		equipo2.setNombre("FC 2");
	}

	private <T, K> void assertMapEquals(Map<T, K> expected, Map<T, K> received) {
		assertThat(!expected.equals(null));
		assertThat(!received.equals(null));

		for (T key : expected.keySet()) {
			assertThat(received.containsKey(key));
			assertThat(received.get(key).equals(expected.get(key)));
		}
	}
}

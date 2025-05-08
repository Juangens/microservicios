package com.sinensia.ligaGFT.business.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sinensia.ligaGFT.business.model.Equipo;
import com.sinensia.ligaGFT.business.model.Partido;
import com.sinensia.ligaGFT.business.services.ClasificacionService;
import com.sinensia.ligaGFT.business.services.EquipoService;

@Service
public class ClasificacionServiceImpl implements ClasificacionService {

	private EquipoService equipoService;
	private List<Partido> misGrandesPartidos = new ArrayList<Partido>();

	public ClasificacionServiceImpl(EquipoService equipoService) {
		this.equipoService = equipoService;
	}

	@Override
	public Map<String, Integer> getClasificacion() {
		Map<String, Integer> miGranMapeado = new HashMap<String, Integer>();
		Collection<List<Partido>> todosLosPartidos = getSamePartidos();

		for (List<Partido> partidos : todosLosPartidos) {
			int lastPartidoIndex = partidos.size() - 1;

			Partido ultimoPartido = partidos.get(lastPartidoIndex);

			Equipo local = equipoService.getEquipoById(ultimoPartido.idEquipoLocal());
			Equipo visitante = equipoService.getEquipoById(ultimoPartido.idVisitance());

			int puntosLocal = 0;
			int puntosVisitante = 0;

			if (miGranMapeado.containsKey(local.getNombre())) {
				puntosLocal = miGranMapeado.get(local.getNombre());
			}

			if (miGranMapeado.containsKey(visitante.getNombre())) {
				puntosVisitante = miGranMapeado.get(visitante.getNombre());
			}

			if (ultimoPartido.golesLocales() == ultimoPartido.golesVisitanes()) {

				puntosLocal++;
				puntosVisitante++;

				miGranMapeado.put(local.getNombre(), puntosLocal);
				miGranMapeado.put(visitante.getNombre(), puntosVisitante);
				continue;
			}

			if (ultimoPartido.golesLocales() < ultimoPartido.golesVisitanes()) {

				puntosVisitante += 3;

				miGranMapeado.put(visitante.getNombre(), puntosVisitante);
				continue;
			}

			if (ultimoPartido.golesLocales() > ultimoPartido.golesVisitanes()) {

				puntosLocal += 3;

				miGranMapeado.put(local.getNombre(), puntosLocal);
				continue;

			}
		}

		return miGranMapeado;
	}

	@Override
	public void añadirPartido(Partido partido) {
		misGrandesPartidos.add(partido);
	}

	public List<Partido> getPartidos() {
		return misGrandesPartidos;
	}

	private Collection<List<Partido>> getSamePartidos() {
		Map<Long, List<Partido>> partidos = new HashMap<Long, List<Partido>>();

		for (Partido p : misGrandesPartidos) {
			if (!partidos.containsKey(p.idPartido())) {
				List<Partido> localPartidos = new ArrayList<>();
				localPartidos.add(p);

				partidos.put(p.idPartido(), localPartidos);

				continue;
			}

			partidos.get(p.idPartido()).add(p);
		}

		return partidos.values();
	}
}

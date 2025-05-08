package com.sinensia.ligaGFT.service;

import com.sinensia.ligaGFT.business.model.LanceMensajeria;
import com.sinensia.ligaGFT.business.model.TipoLance;

public interface LigaService {

	public void procesarLance(LanceMensajeria lance);

	void actualizarMarcador(Long idPartido, TipoLance tipoLance);

	void actualizarEstado(Long idPartido, TipoLance tipoLance);
}

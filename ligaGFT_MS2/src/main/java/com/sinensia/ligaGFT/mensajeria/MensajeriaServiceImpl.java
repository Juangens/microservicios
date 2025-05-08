package com.sinensia.ligaGFT.mensajeria;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.sinensia.ligaGFT.business.model.LanceMensajeria;
import com.sinensia.ligaGFT.service.LigaService;

@Service
public class MensajeriaServiceImpl implements MensajeriaService {

	private final LigaService ligaService;

	public MensajeriaServiceImpl(LigaService ligaService) {
		this.ligaService = ligaService;
	}

	@Override
	@RabbitListener(queues = "cola.lances.grupo3")
	public void recibirLance(LanceMensajeria lance) {
		try {
			System.out.println("🏀 Lance recibido: " + lance);
			ligaService.procesarLance(lance);
		} catch (Exception e) {

		}
	}

}

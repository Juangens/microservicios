package com.sinensia.ligaGFT.presentation.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.sinensia.ligaGFT.business.model.Partido;
import com.sinensia.ligaGFT.business.services.ClasificacionService;

@Component
public class ClasificacionConsumer {

	private ClasificacionService clasificacionService;

	public ClasificacionConsumer(ClasificacionService clasificacionService) {
		this.clasificacionService = clasificacionService;
	}

	@RabbitListener(queues = "cola.clasificacion.grupo5")
	public void recibirPartido(Partido partido) {
		this.clasificacionService.añadirPartido(partido);
		System.out.println("Partido recibido: " + partido);
	}

}

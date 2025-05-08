package com.sinensia.ligaGFT.mensajeria;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.sinensia.ligaGFT.business.model.PartidoMensajeria;

@Service
public class MensajeriaEnviarServiceImpl implements MensajeriaEnviarService {

	private String exchange = "exchange.grupo3.MS2";
	private String routingKey = "clasificacion";

	private final RabbitTemplate rabbitTemplate;

	public MensajeriaEnviarServiceImpl(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void enviarPartido(PartidoMensajeria partido) {
		System.err.println("🏀 Partido enviado: " + partido);
		rabbitTemplate.convertAndSend(exchange, routingKey, partido);
	}

}

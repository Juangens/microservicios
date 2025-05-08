package com.sinensia.ligaGFT.mensajeria;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.sinensia.ligaGFT.business.model.PartidoMensajeria;

class MensajeriaEnviarServiceImplTest {

	private RabbitTemplate rabbitTemplate;
	private MensajeriaEnviarServiceImpl mensajeriaService;

	@BeforeEach
	void setUp() {
		rabbitTemplate = mock(RabbitTemplate.class);
		mensajeriaService = new MensajeriaEnviarServiceImpl(rabbitTemplate);
	}

	@Test
	void testMensajeriaEnviarServiceImpl() {
		assertNotNull(mensajeriaService);
	}

	@Test
	void testEnviarPartido() {
		PartidoMensajeria partido = new PartidoMensajeria(null, null, null, null, 0, 0);
		mensajeriaService.enviarPartido(partido);

		verify(rabbitTemplate, times(1)).convertAndSend("exchange.grupo3.MS2", "clasificacion", partido);
	}

}

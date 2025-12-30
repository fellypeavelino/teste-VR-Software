/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.VRSoftware.VRSoftware;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.VRSoftware.VRSoftware.components.PedidoProducer;
import com.VRSoftware.VRSoftware.dtos.Pedido;
import com.VRSoftware.VRSoftware.enums.StatusOperacao;

/**
 *
 * @author Usuario
 */
@ExtendWith(MockitoExtension.class)
public class PedidoProducerTest {

    @Mock
    RabbitTemplate rabbitTemplate;

    @InjectMocks
    PedidoProducer producer;

    @Test
    void devePublicarPedidoNaFila() {
        Pedido pedido = new Pedido(UUID.randomUUID(), "Produto", 1, LocalDateTime.now());
        producer.enviarPedido(pedido, StatusOperacao.ENVIO);

        verify(rabbitTemplate)
                .convertAndSend("pedidos.entrada.seu-nome", pedido);
    }
}

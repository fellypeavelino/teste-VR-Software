package com.VRSoftware.VRSoftware.components;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.VRSoftware.VRSoftware.dtos.Pedido;

@Component
public class PedidoProducer {
    private final RabbitTemplate rabbitTemplate;

    public PedidoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarPedido(Pedido pedido) {
        rabbitTemplate.convertAndSend("pedidos.entrada.seu-nome", pedido);
    }
}

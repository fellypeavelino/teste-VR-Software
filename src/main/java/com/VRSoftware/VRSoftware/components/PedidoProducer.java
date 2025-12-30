package com.VRSoftware.VRSoftware.components;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.VRSoftware.VRSoftware.dtos.Pedido;
import com.VRSoftware.VRSoftware.enums.QueueEnum;
import com.VRSoftware.VRSoftware.enums.StatusOperacao;

@Component
public class PedidoProducer {

    private final RabbitTemplate rabbitTemplate;

    public PedidoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarPedido(Pedido pedido, StatusOperacao status) {
        switch (status) {
            case ENVIO:
                rabbitTemplate.convertAndSend(QueueEnum.PEDIDOS_ENTRADA_DLQ.getQueueName(), pedido);
                break;
            case SUCESSO:
                rabbitTemplate.convertAndSend(QueueEnum.PEDIDOS_STATUS_SUCESSO.getQueueName(), pedido);
                break;
            case FALHA:
                rabbitTemplate.convertAndSend(QueueEnum.PEDIDOS_STATUS_FALHA.getQueueName(), pedido);
                break;
        }
    }
}

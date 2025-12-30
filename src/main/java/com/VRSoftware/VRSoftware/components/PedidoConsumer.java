package com.VRSoftware.VRSoftware.components;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.VRSoftware.VRSoftware.dtos.Pedido;
import com.VRSoftware.VRSoftware.enums.QueueEnum;
import com.VRSoftware.VRSoftware.enums.StatusOperacao;

@Component
public class PedidoConsumer {

    private final PedidoProducer producer;
    private static final String PEDIDOS_ENTRADA = QueueEnum.PEDIDOS_ENTRADA.getQueueName();

    public PedidoConsumer(PedidoProducer producer) {
        this.producer = producer;
    }

    @RabbitListener(queues = "pedidos.entrada.seu-nome.dlq")
    public void processar(Pedido pedido) throws Exception {
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));

        if (Math.random() < 0.2) {
            producer.enviarPedido(pedido, StatusOperacao.FALHA);
            throw new RuntimeException("Erro no processamento");
        }
        // publicar sucesso
        producer.enviarPedido(pedido, StatusOperacao.SUCESSO);
    }
}

package com.VRSoftware.VRSoftware.components;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.VRSoftware.VRSoftware.dtos.Pedido;
import com.VRSoftware.VRSoftware.enums.QueueEnum;
import com.VRSoftware.VRSoftware.enums.StatusOperacao;
import com.VRSoftware.VRSoftware.enums.StatusPedidoEnum;
import com.VRSoftware.VRSoftware.services.PedidoStatusService;

@Component
public class PedidoConsumer {

    private final PedidoProducer producer;
    private final PedidoStatusService statusService;
    private static final String PEDIDOS_ENTRADA = QueueEnum.PEDIDOS_ENTRADA.getQueueName();

    public PedidoConsumer(PedidoProducer producer, PedidoStatusService statusService) {
        this.producer = producer;
        this.statusService = statusService;
    }

    @RabbitListener(queues = "pedidos.entrada.pedro-felipe.dlq")
    public void processar(Pedido pedido) throws Exception {
        statusService.atualizarStatus(pedido.getId(), StatusPedidoEnum.PROCESSANDO.name(), null);
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));

        if (Math.random() < 0.2) {
            producer.enviarPedido(pedido, StatusOperacao.FALHA);
            statusService.atualizarStatus(pedido.getId(), StatusPedidoEnum.FALHA.name(), "Erro no processamento");
            throw new RuntimeException("Erro no processamento");
        }
        // publicar sucesso
        producer.enviarPedido(pedido, StatusOperacao.SUCESSO);
        statusService.atualizarStatus(pedido.getId(), StatusPedidoEnum.SUCESSO.name(), null);
    }
}

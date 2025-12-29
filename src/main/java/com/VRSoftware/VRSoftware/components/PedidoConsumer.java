package com.VRSoftware.VRSoftware.components;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.VRSoftware.VRSoftware.dtos.Pedido;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class PedidoConsumer {
    @RabbitListener(queues = "pedidos.entrada.seu-nome")
    public void processar(Pedido pedido) throws Exception {
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));

        if (Math.random() < 0.2) {
            throw new RuntimeException("Erro no processamento");
        }

        // publicar sucesso
    }
}

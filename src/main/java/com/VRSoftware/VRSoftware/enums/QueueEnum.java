package com.VRSoftware.VRSoftware.enums;

public enum QueueEnum {
    PEDIDOS_ENTRADA("pedidos.entrada.pedro-felipe"),
    PEDIDOS_ENTRADA_DLQ("pedidos.entrada.pedro-felipe.dlq"),
    PEDIDOS_STATUS_SUCESSO("pedidos.status.sucesso.pedro-felipe"),
    PEDIDOS_STATUS_FALHA("pedidos.status.falha.pedro-felipe");

    private final String queueName;

    // Construtor do Enum
    QueueEnum(String queueName) {
        this.queueName = queueName;
    }

    // Método para recuperar o valor da string
    public String getQueueName() {
        return queueName;
    }
}

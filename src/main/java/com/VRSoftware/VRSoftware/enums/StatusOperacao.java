package com.VRSoftware.VRSoftware.enums;

public enum StatusOperacao {
    ENVIO("Operação enviou um pedido para processamento"),
    SUCESSO("Operação realizada com sucesso"),
    FALHA("Ocorreu um erro ao processar a solicitação");

    private final String descricao;

    StatusOperacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

package com.VRSoftware.VRSoftware.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public class StatusPedido {
    private UUID idPedido;
    private String status; // RECEBIDO | PROCESSANDO | SUCESSO | FALHA
    private LocalDateTime dataProcessamento;
    private String mensagemErro;
}

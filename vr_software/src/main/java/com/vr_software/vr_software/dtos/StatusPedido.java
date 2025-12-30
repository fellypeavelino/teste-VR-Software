/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vr_software.vr_software.dtos;

import com.vr_software.vr_software.enums.StatusPedidoEnum;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author Usuario
 */
public class StatusPedido {

    private UUID idPedido;
    private StatusPedidoEnum status; // RECEBIDO | PROCESSANDO | SUCESSO | FALHA
    private LocalDateTime dataProcessamento;
    private String mensagemErro;
    
    public StatusPedido(){}

    public StatusPedido(UUID idPedido, StatusPedidoEnum status, String mensagemErro) {
        this.idPedido = idPedido;
        this.status = status;
        this.dataProcessamento = LocalDateTime.now(); // Define a data no momento da criação
        this.mensagemErro = mensagemErro;
    }

    public UUID getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(UUID idPedido) {
        this.idPedido = idPedido;
    }

    public StatusPedidoEnum getStatus() {
        return StatusPedidoEnum.valueOf(status.name());
    }

    public void setStatus(String status) {
        this.status = StatusPedidoEnum.valueOf(status);
    }

    public LocalDateTime getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(LocalDateTime dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }
}


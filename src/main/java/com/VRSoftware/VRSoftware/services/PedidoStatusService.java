/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.VRSoftware.VRSoftware.services;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.VRSoftware.VRSoftware.dtos.StatusPedido;
import com.VRSoftware.VRSoftware.enums.StatusPedidoEnum;

/**
 *
 * @author Usuario
 */
@Service
public class PedidoStatusService {

    private final Map<UUID, StatusPedido> statusMap = new ConcurrentHashMap<>();

    public void atualizarStatus(UUID id, String status, String mensagemErro) {
        StatusPedido statusPedido = new StatusPedido(id, StatusPedidoEnum.valueOf(status), mensagemErro);
        statusMap.put(id, statusPedido);
    }

    public StatusPedido obterStatus(UUID id) {
        return statusMap.get(id);
    }
}

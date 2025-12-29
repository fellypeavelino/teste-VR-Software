/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.VRSoftware.VRSoftware.services;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class PedidoStatusService {
    private final Map<UUID, String> statusMap = new ConcurrentHashMap<>();

    public void atualizarStatus(UUID id, String status) {
        statusMap.put(id, status);
    }

    public String obterStatus(UUID id) {
        return statusMap.getOrDefault(id, "DESCONHECIDO");
    }
}

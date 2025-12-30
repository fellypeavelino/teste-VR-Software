package com.VRSoftware.VRSoftware.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.VRSoftware.VRSoftware.components.PedidoProducer;
import com.VRSoftware.VRSoftware.dtos.Pedido;
import com.VRSoftware.VRSoftware.dtos.StatusPedido;
import com.VRSoftware.VRSoftware.enums.StatusOperacao;
import com.VRSoftware.VRSoftware.enums.StatusPedidoEnum;
import com.VRSoftware.VRSoftware.services.PedidoStatusService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoProducer producer;
    private final PedidoStatusService statusService;

    public PedidoController(PedidoProducer producer, PedidoStatusService statusService) {
        this.producer = producer;
        this.statusService = statusService;
    }

    @PostMapping
    public ResponseEntity<?> criarPedido(@Valid @RequestBody Pedido pedido) {
        statusService.atualizarStatus(pedido.getId(), StatusPedidoEnum.RECEBIDO.name(), null);
        producer.enviarPedido(pedido, StatusOperacao.ENVIO);
        return ResponseEntity.accepted().body(pedido.getId());
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<StatusPedido> status(@PathVariable UUID id) {
        return ResponseEntity.ok(statusService.obterStatus(id));
    }
}

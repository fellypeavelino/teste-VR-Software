/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vr_software.vr_software.frame;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vr_software.vr_software.dtos.Pedido;
import com.vr_software.vr_software.dtos.StatusPedido;
import com.vr_software.vr_software.http.PedidoHttpClient;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.Timer;
import javax.swing.SwingUtilities;

import java.util.Map;


/**
 *
 * @author Usuario
 */
public class PedidoFrame extends JFrame {

    private JTextField produtoField;
    private JTextField quantidadeField;
    private JTextArea statusArea;

    private PedidoHttpClient client = new PedidoHttpClient();
    private Map<UUID, String> pedidosPendentes = new ConcurrentHashMap<>();

    public PedidoFrame() {
        setTitle("Sistema de Pedidos");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(3, 2));

        produtoField = new JTextField();
        quantidadeField = new JTextField();

        JButton enviarButton = new JButton("Enviar Pedido");

        form.add(new JLabel("Produto:"));
        form.add(produtoField);
        form.add(new JLabel("Quantidade:"));
        form.add(quantidadeField);
        form.add(enviarButton);

        statusArea = new JTextArea();
        statusArea.setEditable(false);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(statusArea), BorderLayout.CENTER);

        enviarButton.addActionListener(e -> enviarPedido());

        iniciarPolling();
    }

    private void enviarPedido() {
        try {
            Pedido pedido = new Pedido(
                UUID.randomUUID(),
                produtoField.getText(),
                Integer.parseInt(quantidadeField.getText()),
                LocalDateTime.now()
            );

            new Thread(() -> {
                try {
                    client.enviarPedido(pedido);
                    pedidosPendentes.put(pedido.getId(), "AGUARDANDO PROCESSO");

                    SwingUtilities.invokeLater(() ->
                        statusArea.append(pedido.getId() + " - ENVIADO\n")
                    );

                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(this,
                            "Erro ao enviar pedido", "Erro", JOptionPane.ERROR_MESSAGE)
                    );
                }
            }).start();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Dados inválidos", "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void iniciarPolling() {
        Timer timer = new Timer(4000, e -> {
            pedidosPendentes.forEach((id, status) -> {
                try {
                    String novoStatus = client.consultarStatus(id);

                    ObjectMapper mapper = new ObjectMapper();
                    mapper.registerModule(new JavaTimeModule());

                    StatusPedido statusPedido = mapper.readValue(novoStatus, StatusPedido.class);
                    if ("SUCESSO".equals(statusPedido.getStatus().name()) || "FALHA".equals(statusPedido.getStatus().name())) {
                        pedidosPendentes.remove(id);

                        SwingUtilities.invokeLater(() ->
                            statusArea.append(id + " - " + statusPedido.getStatus().name() + "\n")
                        );
                    }

                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            });
        });
        timer.start();
    }
}

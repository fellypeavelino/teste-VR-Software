/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vr_software.vr_software.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vr_software.vr_software.dtos.Pedido;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.Duration;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.JsonNode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
/**
 *
 * @author Usuario
 */
public class PedidoHttpClient {

    private static final String BASE_URL = "http://localhost:8080/api/pedidos";
    private final ObjectMapper mapper;

    public PedidoHttpClient() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public void enviarPedido(Pedido pedido) throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(mapper.writeValueAsBytes(pedido));
        }

        if (conn.getResponseCode() != 202) {
            throw new RuntimeException("Erro ao enviar pedido");
        }
    }

    public String consultarStatus(UUID id) throws IOException {
        URL url = new URL(BASE_URL + "/status/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");

        try (BufferedReader br =
                 new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            return br.readLine();
        }
    }
}


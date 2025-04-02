package com.example.kards.services;

import com.example.kards.domain.Deck;
import com.example.kards.domain.StartTruco;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequestMapping("/start")
public class StartTrucoService {
    private String url = "https://deckofcardsapi.com/api/deck";
    private String uri;
    private String token; /* Criar posteriormente */

    @GetMapping("/truco")
    public StartTruco getStartTruco(int deck_count) throws IOException, InterruptedException {
        uri = "/new/shuffle/?deck_count=" + deck_count;
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url+uri)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro na requisição " + response.statusCode());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        StartTruco startTruco = objectMapper.readValue(response.body(), StartTruco.class);

        if (!startTruco.isSuccess()){
            throw new RuntimeException("A API retornou erro ao criar o baralho.");
        }

        return startTruco;
    }

    @GetMapping("/decks")
    public Deck generateDecks(int deck_quantity) throws IOException, InterruptedException {
        uri = "/new/draw/?count="+deck_quantity;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url+uri)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro na requisição " + response.statusCode());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Deck deck = objectMapper.readValue(response.body(), Deck.class);

        if (!deck.isSuccess()){
            throw new RuntimeException("A API retornou erro ao criar as mãos");
        }

        return deck;
    }
}

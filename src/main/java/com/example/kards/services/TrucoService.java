package com.example.kards.services;

import com.example.kards.domain.*;
import com.example.kards.repositories.DeckRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class TrucoService {

    private String url = "https://deckofcardsapi.com/api/deck";

    @Autowired
    private DeckRepository deckRepository;

    private <T> T callApi(String endpoint, Class<T> responseType)
            throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + endpoint))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro na requisição " + response.statusCode());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), responseType);
    }

    // ===================================================
    // 1) Cria o baralho embaralhado e salva no banco
    // ===================================================
    public DeckEntity getStartTruco(int deckCount) throws IOException, InterruptedException {

        String endpoint = "/new/shuffle/?deck_count=" + deckCount;

        StartTruco startTruco = callApi(endpoint, StartTruco.class);

        if (!startTruco.isSuccess()) {
            throw new RuntimeException("A API retornou erro ao criar o baralho.");
        }

        // Criar a entidade e salvar apenas os dados do deck (sem cartas ainda)
        DeckEntity entity = new DeckEntity();
        entity.setId(startTruco.getDeck_id());
        entity.setRemaining(startTruco.getRemaining());
        entity.setCards(List.of()); // ainda não existem cartas nesse momento

        return deckRepository.save(entity);
    }

    public DeckEntity generateDecks(int deckQuantity) throws IOException, InterruptedException {

        DeckEntity deckEntity = getStartTruco(1); // gera um deck novo
        String deckId = deckEntity.getId();

        String endpoint = "/" + deckId + "/draw/?count=" + deckQuantity;

        Deck deckApiResponse = callApi(endpoint, Deck.class);

        if (!deckApiResponse.isSuccess()) {
            throw new RuntimeException("A API retornou erro ao comprar cartas.");
        }

        List<CardEntity> cards = deckApiResponse.getCards().stream().map(c -> {
            CardEntity ce = new CardEntity();
            ce.setCode(c.getCode());
            ce.setSuit(c.getSuit());
            ce.setValue(c.getValue());
            ce.setImage(c.getImage());
            ce.setDeck(deckEntity);
            return ce;
        }).toList();

        deckEntity.setCards(cards);
        deckEntity.setRemaining(deckApiResponse.getRemaining());

        return deckRepository.save(deckEntity);
    }

    public PilesResponse addCardsToPile(String deckId, String pileName, List<String> cards)
            throws IOException, InterruptedException {

        String joined = String.join(",", cards);
        String endpoint = "/" + deckId + "/pile/" + pileName + "/add/?cards=" + joined;

        return callApi(endpoint, PilesResponse.class);
    }

    public PilesResponse listPileCards(String deckId, String pileName)
            throws IOException, InterruptedException {

        String endpoint = "/" + deckId + "/pile/" + pileName + "/list/";

        return callApi(endpoint, PilesResponse.class);
    }
}

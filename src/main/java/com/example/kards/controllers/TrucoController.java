package com.example.kards.controllers;

import com.example.kards.domain.Deck;
import com.example.kards.domain.DeckEntity;
import com.example.kards.domain.PilesResponse;
import com.example.kards.domain.StartTruco;
import com.example.kards.services.TrucoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/truco")
public class TrucoController {
        @Autowired
        private TrucoService trucoService;

        @GetMapping()
        public ResponseEntity<?> getStartTruco(){
                try {
                        System.out.println("Iniciando Truco...");
                        DeckEntity deck = trucoService.getStartTruco(4);
                        return ResponseEntity.ok(deck);
                } catch (RuntimeException e) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + e.getMessage());
                }
        }

        @GetMapping("/gerar")
        public ResponseEntity<?> generateDecks(){
                try {
                        System.out.println("Gerando mãos...");
                        DeckEntity deck = trucoService.generateDecks(4);
                        return ResponseEntity.ok(deck);
                } catch (RuntimeException e) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + e.getMessage());
                }
        }

        @PostMapping("deck/{deckId}/pile/{pileName}/add")
        public PilesResponse addToPile(
                @PathVariable String deckId,
                @PathVariable String pileName,
                @RequestBody List<String> cards
        ) throws Exception {
                return trucoService.addCardsToPile(deckId, pileName, cards);
        }

        @GetMapping("deck/{deckId}/pile/{pileName}")
        public PilesResponse listPile(
                @PathVariable String deckId,
                @PathVariable String pileName
        ) throws Exception {
                return trucoService.listPileCards(deckId, pileName);
        }
}
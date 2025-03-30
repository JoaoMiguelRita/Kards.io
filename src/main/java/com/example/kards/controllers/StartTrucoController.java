package com.example.kards.controllers;

import com.example.kards.domain.StartTruco;
import com.example.kards.services.StartTrucoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/start")
public class StartTrucoController {
        @Autowired
        private StartTrucoService startTrucoService;

        @GetMapping("/truco")
        public ResponseEntity<?> getStartTruco(){
                try {
                        System.out.println("Iniciando Truco...");
                        StartTruco startTruco = startTrucoService.getStartTruco(4);
                        return ResponseEntity.ok(startTruco);
                } catch (RuntimeException e) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + e.getMessage());
                }
        }

}
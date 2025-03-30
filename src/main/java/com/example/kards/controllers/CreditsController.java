package com.example.kards.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class CreditsController {
    @RequestMapping("/credits")
    public Map<String, String> credits() {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("creator", "João Miguel Fortunato Rita");
        response.put("project", "Kards.io");
        response.put("about", "This is a web game of cards.");

        return response;
    }
}

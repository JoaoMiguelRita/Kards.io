package com.example.kards.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "deck")
public class DeckEntity {

    @Id
    private String id;

    private float remaining;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL)
    private List<CardEntity> cards;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getRemaining() {
        return remaining;
    }

    public void setRemaining(float remaining) {
        this.remaining = remaining;
    }

    public List<CardEntity> getCards() {
        return cards;
    }

    public void setCards(List<CardEntity> cards) {
        this.cards = cards;
    }
}

package com.example.kards.domain;

import java.util.List;

public class PileInfo {
    private List<PileCard> cards;

    private int remaining;

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public List<PileCard> getCards() {
        return cards;
    }

    public void setCards(List<PileCard> cards) {
        this.cards = cards;
    }
}

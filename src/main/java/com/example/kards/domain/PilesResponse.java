package com.example.kards.domain;

import java.util.Map;

public class PilesResponse {
    private boolean success;
    private String deck_id;
    private int remaining;

    // player1, player2, dealer, etc.
    private Map<String, PileInfo> piles;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDeck_id() {
        return deck_id;
    }

    public void setDeck_id(String deck_id) {
        this.deck_id = deck_id;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public Map<String, PileInfo> getPiles() {
        return piles;
    }

    public void setPiles(Map<String, PileInfo> piles) {
        this.piles = piles;
    }
}

package com.famvdploeg.aoc.domain;

import lombok.Getter;

@Getter
public enum GameOutcome {
    LOSE(0),
    TIE(3),
    WIN(6);

    private final int score;

    GameOutcome(int score) {
        this.score = score;
    }

    public static GameOutcome fromValue(char value) {
        return switch (value) {
            case 'X' -> LOSE;
            case 'Y' -> TIE;
            case 'Z' -> WIN;
            default -> throw new IllegalArgumentException("Invalid character value: " + value);
        };
    }
}

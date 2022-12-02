package com.famvdploeg.aoc.domain;

public enum RockPaperScissors {
    ROCK,
    PAPER,
    SCISSORS;

    public static RockPaperScissors fromValue(char value) {
        return switch (value) {
            case 'A', 'X' -> ROCK;
            case 'B', 'Y' -> PAPER;
            case 'C', 'Z' -> SCISSORS;
            default -> throw new IllegalArgumentException("Invalid character value: " + value);
        };
    }
}

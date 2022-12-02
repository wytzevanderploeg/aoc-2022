package com.famvdploeg.aoc.rps;

import com.famvdploeg.aoc.domain.GameOutcome;
import com.famvdploeg.aoc.domain.RockPaperScissors;

import java.util.HashMap;
import java.util.Map;

import static com.famvdploeg.aoc.domain.RockPaperScissors.*;

public class RockPaperScissorsGame {

    private static final int WIN = 6;
    private static final int TIE = 3;
    private static final int LOSS = 0;

    private final Map<RockPaperScissors, RockPaperScissors> beats = new HashMap<>();

    public RockPaperScissorsGame() {
        beats.put(ROCK, SCISSORS);
        beats.put(PAPER, ROCK);
        beats.put(SCISSORS, PAPER);
    }

    public int score(RockPaperScissors yours, RockPaperScissors theirs) {
        // base score
        int score = yours.ordinal() + 1;

        // who won?
        return score + getOutcome(yours, theirs);
    }

    public int score(GameOutcome yours, RockPaperScissors theirs) {
        RockPaperScissors choice;

        switch (yours) {
            case TIE -> choice = theirs;
            case WIN -> choice = getWinningChoice(theirs);
            case LOSE -> choice = getLosingChoice(theirs);
            default -> throw new IllegalArgumentException(String.format("Unsupported outcome: [%s]", yours));
        }

        return score(choice, theirs);
    }

    private RockPaperScissors getWinningChoice(RockPaperScissors theirs) {
        for (Map.Entry<RockPaperScissors, RockPaperScissors> entry : beats.entrySet()) {
            if (theirs.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        throw new IllegalStateException(String.format("No winning choice for [%s]", theirs));
    }

    private RockPaperScissors getLosingChoice(RockPaperScissors theirs) {
        for (Map.Entry<RockPaperScissors, RockPaperScissors> entry : beats.entrySet()) {
            if (theirs.equals(entry.getKey())) {
                return entry.getValue();
            }
        }
        throw new IllegalStateException(String.format("No winning choice for [%s]", theirs));
    }

    private int getOutcome(RockPaperScissors yours, RockPaperScissors theirs) {
        if (yours.equals(theirs)) {
            return TIE;
        }

        return beats.get(yours).equals(theirs) ? WIN : LOSS;
    }
}

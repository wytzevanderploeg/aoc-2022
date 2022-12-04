package com.famvdploeg.aoc.day2;

import com.famvdploeg.aoc.domain.GameOutcome;
import com.famvdploeg.aoc.domain.RockPaperScissors;
import com.famvdploeg.aoc.rps.RockPaperScissorsGame;
import com.famvdploeg.aoc.util.ResourceReader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
public class Day2 {

    @Test
    void partOne() {
        RockPaperScissorsGame game = new RockPaperScissorsGame();
        List<String> input = ResourceReader.readResource("/input/day2/input.txt");

        int total = 0;
        for (String line : input) {
            String[] items = line.split("[\\s+]");
            if (items.length != 2) {
                throw new IllegalStateException("Too many items on one line");
            }
            RockPaperScissors theirs = RockPaperScissors.fromValue(items[0].charAt(0));
            RockPaperScissors yours = RockPaperScissors.fromValue(items[1].charAt(0));

            int score = game.score(yours, theirs);
            log.debug("Score: {}", score);
            total += score;
        }

        log.info("Total: {}", total);
    }

    @Test
    void partTwo() {
        RockPaperScissorsGame game = new RockPaperScissorsGame();
        List<String> input = ResourceReader.readResource("/input/day2/input.txt");

        int total = 0;
        for (String line : input) {
            String[] items = line.split("[\\s+]");
            if (items.length != 2) {
                throw new IllegalStateException("Too many items on one line");
            }
            RockPaperScissors theirs = RockPaperScissors.fromValue(items[0].charAt(0));
            GameOutcome yours = GameOutcome.fromValue(items[1].charAt(0));

            int score = game.score(yours, theirs);
            log.debug("Score: {}", score);
            total += score;
        }

        log.info("Total: {}", total);
    }
}

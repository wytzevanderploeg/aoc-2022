package com.famvdploeg.aoc.day1;

import com.famvdploeg.aoc.domain.Elf;
import com.famvdploeg.aoc.domain.Food;
import com.famvdploeg.aoc.util.ResourceReader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
public class Day1 {

    @Test
    void partOne() {
        List<Elf> elves = this.loadElves();

        Optional<Elf> result = elves.stream()
                .max(Comparator.comparingInt(Elf::getTotalCalories));

        result.ifPresent(e -> log.info("Most calories: [{}]", e));
    }

    @Test
    void partTwo() {
        List<Elf> elves = new ArrayList<>();

        List<String> input = ResourceReader.readResource("/input/day1/input.txt");

        int i = 0;
        Elf elf = new Elf("elf-" + i++);

        for (String line : input) {
            if (line.isBlank()) {
                elves.add(elf);
                elf = new Elf("elf-" + i++);
            } else {
                try {
                    int calories = Integer.parseInt(line);
                    Food food = new Food("unknown", calories);
                    elf.addFood(food);
                } catch (NumberFormatException ex) {
                    log.warn("Could not parse calories: [{}]", line);
                }
            }
        }
        elves.add(elf);

        List<Elf> topThree = elves.stream()
                .sorted(Comparator.comparingInt(Elf::getTotalCalories).reversed())
                .limit(3)
                .toList();

        int total = topThree.stream()
                .map(Elf::getTotalCalories)
                .reduce(0, Integer::sum);

        log.info("Total calories: [{}]", total);
    }

    private List<Elf> loadElves() {
        List<Elf> elves = new ArrayList<>();

        List<String> input = ResourceReader.readResource("/input/day1/input.txt");

        int i = 0;
        Elf elf = new Elf("elf-" + i++);

        for (String line : input) {
            if (line.isBlank()) {
                elves.add(elf);
                elf = new Elf("elf-" + i++);
            } else {
                try {
                    int calories = Integer.parseInt(line);
                    Food food = new Food("unknown", calories);
                    elf.addFood(food);
                } catch (NumberFormatException ex) {
                    log.warn("Could not parse calories: [{}]", line);
                }
            }
        }
        elves.add(elf);

        return elves;
    }
}

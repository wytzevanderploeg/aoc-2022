package com.famvdploeg.aoc.day3;

import com.famvdploeg.aoc.util.Resource;
import com.famvdploeg.aoc.util.ResourceReader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day3 {

    private static final int START_LOWERCASE = 96;
    private static final int START_UPPERCASE = 38;

    @Test
    void partOne() {
        List<String> items = ResourceReader.readResource("/input/day3/input.txt");
        int totalPriority = 0;
        for (String item : items) {
            if (item.length() % 2 == 1) {
                throw new IllegalStateException("Item length odd");
            }
            int half = item.length() / 2;
            String compartmentOne = item.substring(0, half);
            String compartmentTwo = item.substring(half);

            Set<Character> intersection = getIntersection(List.of(compartmentOne, compartmentTwo));
            log.debug("Intersection: [{}]", intersection);
            totalPriority += this.toCodePoint(intersection);
        }
        log.info("Priority: [{}]", totalPriority);
    }

    @Test
    void partTwo() {
        List<String> items = ResourceReader.readResource("/input/day3/input.txt");

        List<String> group = new ArrayList<>();
        int totalPriority = 0;
        for (String item : items) {
            group.add(item);

            if (group.size() % 3 == 0) {
                log.debug("Group complete: [{}]", group);

                Set<Character> intersection = getIntersection(group);
                totalPriority += this.toCodePoint(intersection);
                group = new ArrayList<>();
            }
        }
        log.info("Priority: [{}]", totalPriority);
    }

    @Test
    void partTwoRxJava() {
        Resource.ResourceHandler handler = lines$ -> lines$
                .onBackpressureBuffer(3)
                .buffer(3)
                .map(this::getIntersection)
                .map(this::toCodePoint)
                .reduce(0, Integer::sum)
                .subscribe(total -> log.info("Total: {}", total))
                .dispose();

        Resource.flowable("/input/day3/input.txt", handler);
    }

    private Set<Character> getIntersection(List<String> values) {
        Stream<Character> head = values.get(0).chars()
                .distinct()
                .mapToObj(i -> (char) i);

        for (String tail : values.subList(1, values.size())) {
            head = head.filter(c ->
                    tail.chars().anyMatch(value -> c == value));
        }

        return head.collect(Collectors.toSet());
    }

    private int toCodePoint(Set<Character> values) {
        return values.stream()
                .map(c -> {
                    if (Character.isLowerCase(c)) {
                        log.debug("Char value: [{}]", c - START_LOWERCASE);
                        return c - START_LOWERCASE;
                    }
                    if (Character.isUpperCase(c)) {
                        log.debug("Char value: [{}]", c - START_UPPERCASE);
                        return c - START_UPPERCASE;
                    }
                    throw new IllegalArgumentException("Invalid argument");
                })
                .reduce(0, Integer::sum);
    }
}

package com.famvdploeg.aoc.day4;

import com.famvdploeg.aoc.AbstractBaseTest;
import com.famvdploeg.aoc.fn.Tuple;
import com.famvdploeg.aoc.util.Resource;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class Day4 extends AbstractBaseTest {

    @Test
    void partOne() {
        Resource.ResourceHandler handler = line$ -> line$
                .onBackpressureBuffer(3)
                .map(line -> line.split(","))
                .map(this::toSections)
                .filter(tuple -> functions.fullMatch(tuple.getFirst(), tuple.getSecond()))
                .doOnEach(entry -> {
                    log.debug("Value: {}", entry);
                })
                .count()
                .subscribe(total -> log.info("Total: {}", total))
                .dispose();
        Resource.flowable("/input/day4/input.txt", handler);
    }

    @Test
    void partTwo() {
        Resource.ResourceHandler handler = line$ -> line$
                .onBackpressureBuffer(3)
                .map(line -> line.split(","))
                .map(this::toSections)
                .map(t -> functions.getIntersection(t.getFirst(), t.getSecond()))
                .filter(entry -> !entry.isEmpty())
                .count()
                .subscribe(total -> log.info("Total: {}", total))
                .dispose();
        Resource.flowable("/input/day4/input.txt", handler);
    }

    private Tuple<List<Integer>> toSections(String[] parts) {
        Tuple<List<Integer>> result = new Tuple<>(new ArrayList<>(), new ArrayList<>());
        for (int i = 0; i < parts.length; i++) {
            String[] bounds = parts[i].split("-");
            int lower = Integer.parseInt(bounds[0]);
            int upper = Integer.parseInt(bounds[1]);
            for (int j = lower; j <= upper; j++) {
                result.get(i)
                        .add(j);
            }
        }
        return result;
    }
}

package com.famvdploeg.aoc.day5;

import com.famvdploeg.aoc.AbstractBaseTest;
import com.famvdploeg.aoc.fn.Tuple;
import com.famvdploeg.aoc.util.Resource;
import com.famvdploeg.aoc.util.ResourceReader;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class Day5 extends AbstractBaseTest {

    @Test
    void partOne() {
        // execute
        String result = this.stackCrates(true);

        // verify
        Assertions.assertThat(result)
                .isEqualTo("CWMTGHBDW");
    }

    @Test
    void partTwo() {
        // execute
        String result = this.stackCrates(false);

        // verify
        Assertions.assertThat(result)
                .isEqualTo("SSCGWJCRB");
    }

    private String stackCrates(boolean reverseCollection) {
        List<String> lines = ResourceReader.readResource("/input/day5/input.txt");

        List<String> cratesLines = lines.stream()
                .takeWhile(line -> !line.isBlank())
                .collect(Collectors.toList());

        List<String> operations = lines.stream()
                .skip(cratesLines.size())
                .toList();

        String nrStacksLine = cratesLines.remove(cratesLines.size() - 1);
        int nrStacks = Stream.of(nrStacksLine.split("\\s+"))
                .filter(item -> !item.isBlank())
                .map(Integer::parseInt)
                .max(Comparator.naturalOrder())
                .orElseThrow();

        log.debug("Nr stacks: {}", nrStacks);

        Map<Integer, List<String>> stacks = new LinkedHashMap<>();
        IntStream.rangeClosed(1, nrStacks)
                .forEach(idx -> stacks.put(idx, new ArrayList<>()));

        final Pattern cratePattern = Pattern.compile("(\\s{3}|\\[\\w])\\s?");
        Observable.fromIterable(cratesLines)
                .doOnEach(val -> log.info("CrateLine: [{}]", val))
                .map(cratePattern::matcher)
                .switchMap(matcher -> functions.toMatches(matcher)
                        .zipWith(
                                Observable.range(1, nrStacks),
                                (c, i) -> Pair.with(i, c))
                        .filter(pair -> !pair.getValue1().isBlank())
                ).subscribe(match -> {
                    log.debug("Match: [{}]={}", match.getValue0(), match.getValue1());
                    stacks.get(match.getValue0())
                            .add(match.getValue1());
                })
                .dispose();

        stacks.forEach((key, value) -> {
            Collections.reverse(value);
            log.debug("Original: {}: {}", key, value);
        });

        Pattern opsPattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)", Pattern.CASE_INSENSITIVE);
        for (String ops : operations) {
            if (ops.isBlank()) {
                continue;
            }
            Matcher m = opsPattern.matcher(ops);
            if (!m.matches()) {
                throw new IllegalArgumentException("Could not parse operation");
            }
            int amount = Integer.parseInt(m.group(1));
            int fromStack = Integer.parseInt(m.group(2));
            int toStack = Integer.parseInt(m.group(3));

            log.debug("Move {} from {} to {}", amount, fromStack, toStack);

            List<String> from = stacks.get(fromStack);
            List<String> to = stacks.get(toStack);

            List<String> move = from.subList(from.size() - amount, from.size());
            log.debug("Subset: {}", move);

            if (reverseCollection) {
                Collections.reverse(move);
            }

            to.addAll(move);
            move.clear();

            stacks.forEach((key, value) -> log.debug("{}: {}", key, value));
        }

        stacks.forEach((key, value) -> log.info("{}: {}", key, value));

        String combined = stacks.values().stream()
                .map(stack -> stack.get(stack.size() - 1))
                .collect(Collectors.joining());
        combined = combined.replaceAll("[\\[\\]]", "");

        log.info("Result: {}", combined);

        return combined;
    }
}

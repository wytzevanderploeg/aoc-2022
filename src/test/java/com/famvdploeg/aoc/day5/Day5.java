package com.famvdploeg.aoc.day5;

import com.famvdploeg.aoc.AbstractBaseTest;
import com.famvdploeg.aoc.util.Resource;
import com.famvdploeg.aoc.util.ResourceReader;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;
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
        this.stackCrates(true);
    }

    @Test
    void partTwo() {
        this.stackCrates(false);
    }

    private void stackCrates(boolean reverseCollection) {
        List<String> lines = ResourceReader.readResource("/input/day5/input.txt");

        List<String> cratesLines = lines.stream()
                .takeWhile(line -> !line.trim().isEmpty())
                .collect(Collectors.toList());

        String nrStacksLine = cratesLines.remove(cratesLines.size() - 1);
        int nrStacks = Stream.of(nrStacksLine.split("\\s+"))
                .filter(item -> !item.isBlank())
                .map(Integer::parseInt)
                .max(Comparator.naturalOrder())
                .orElseThrow();

        log.debug("Nr stacks: {}", nrStacks);

        Map<Integer, List<String>> stacks = new LinkedHashMap<>();
        for (int i = 1; i <= nrStacks; i++) {
            stacks.put(i, new ArrayList<>());
        }

        Pattern cratePattern = Pattern.compile("(\\s{3}|\\[\\w])");
        for (String cratesLine : cratesLines) {
            int idx = 0;
            int searchIdx = 0;
            Matcher m = cratePattern.matcher(cratesLine);
            while (m.find(searchIdx)) {
                idx++;
                String crate = m.group(1);
                searchIdx = Math.min(m.end() + 1, cratesLine.length() - 1);
                if (crate.isBlank()) {
                    continue;
                }
                log.debug("Crate: {}", crate);
                stacks.get(idx)
                        .add(crate);
            }
        }

        stacks.forEach((key, value) -> {
            Collections.reverse(value);
            log.debug("Original: {}: {}", key, value);
        });

        List<String> operations = lines.stream()
                .skip(cratesLines.size() + 2) // 1 removed, 1 white line
                .toList();
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
    }
}

package com.famvdploeg.aoc.day7;

import com.famvdploeg.aoc.AbstractBaseTest;
import com.famvdploeg.aoc.util.ResourceReader;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Slf4j
public class Day7 extends AbstractBaseTest {

    private static final Pattern OPERATOR = Pattern.compile("\\$ (\\w+)( (.*?)|)");

    @Test
    void partOne() {
        List<String> lines = ResourceReader.readResource("/input/day7/input.txt");

        Path curPath = Path.of("/");
        Map<Path, List<Pair<Integer, String>>> dirSizes = new HashMap<>();

        String state = null;

        for (String line : lines) {
            if (line.startsWith("$")) {
                state = null;

                Matcher m = OPERATOR.matcher(line);
                if (!m.matches()) {
                    throw new IllegalStateException("Unexpected operator: " + line);
                }
                String operator = m.group(1);
                String argument = m.group(3);

                if ("cd".equals(operator)) {
                    curPath = Path.of(curPath.toString(), argument);
                    curPath = curPath.normalize();
                    dirSizes.putIfAbsent(curPath, new ArrayList<>());
                } else if ("ls".equals(operator)) {
                    state = "ls";
                }
            } else if ("ls".equals(state)) {
                if (line.startsWith("dir")) {
                    Path subDir = Path.of(curPath.toString(), line.substring(4));
                    dirSizes.putIfAbsent(subDir, new ArrayList<>());
                } else {
                    String[] parts = line.split(" ");
                    dirSizes.get(curPath)
                            .add(new Pair<Integer, String>(Integer.parseInt(parts[0]), parts[1]));
                }
            }
        }

        log.info("Contents: {}", dirSizes);

        int sum = dirSizes.keySet().stream()
                .map(path -> new Pair<Path, Integer>(path, getDirSize(dirSizes, path)))
                .filter(pair -> pair.getValue1() < 100000)
                .peek(pair -> log.info("[{}]: {}", pair.getValue0(), pair.getValue1()))
                .map(Pair::getValue1)
                .reduce(0, Integer::sum);
        log.info("Sum: {}", sum);
    }

    private int getDirSize(Map<Path, List<Pair<Integer, String>>> dirSizes, Path path) {
        return dirSizes.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(path))
                .flatMap(entry -> entry.getValue().stream())
                .map(Pair::getValue0)
                .reduce(0, Integer::sum);
    }

    @Test
    void partTwo() {
        List<String> lines = ResourceReader.readResource("/input/day7/input.txt");

        Path curPath = Path.of("/");
        Map<Path, List<Pair<Integer, String>>> dirSizes = new HashMap<>();

        String state = null;

        for (String line : lines) {
            if (line.startsWith("$")) {
                state = null;

                Matcher m = OPERATOR.matcher(line);
                if (!m.matches()) {
                    throw new IllegalStateException("Unexpected operator: " + line);
                }
                String operator = m.group(1);
                String argument = m.group(3);

                if ("cd".equals(operator)) {
                    curPath = Path.of(curPath.toString(), argument);
                    curPath = curPath.normalize();
                    dirSizes.putIfAbsent(curPath, new ArrayList<>());
                } else if ("ls".equals(operator)) {
                    state = "ls";
                }
            } else if ("ls".equals(state)) {
                if (line.startsWith("dir")) {
                    Path subDir = Path.of(curPath.toString(), line.substring(4));
                    dirSizes.putIfAbsent(subDir, new ArrayList<>());
                } else {
                    String[] parts = line.split(" ");
                    dirSizes.get(curPath)
                            .add(new Pair(Integer.parseInt(parts[0]), parts[1]));
                }
            }
        }

        int diskSpaceUsed = getDirSize(dirSizes, Path.of("/"));
        int unused = 70000000 - diskSpaceUsed;
        dirSizes.keySet().stream()
                .map(path -> new Pair<Path, Integer>(path, getDirSize(dirSizes, path)))
                .filter(pair -> unused + pair.getValue1() > 30000000)
                .min((one, two) -> one.getValue1().compareTo(two.getValue1()))
                .ifPresent(toBeDeleted -> log.info("To be deleted: {}", toBeDeleted));
    }

}

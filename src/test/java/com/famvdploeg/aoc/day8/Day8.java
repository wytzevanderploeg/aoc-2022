package com.famvdploeg.aoc.day8;

import com.famvdploeg.aoc.AbstractBaseTest;
import com.famvdploeg.aoc.util.ResourceReader;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day8 extends AbstractBaseTest {

    @Test
    void partOne() {
        List<String> lines = ResourceReader.readResource("/input/day8/input.txt");
        short[][] grid = loadGrid(lines);

        if (grid == null) {
            throw new IllegalStateException("Grid should be loaded");
        }

        int totalVisible = grid.length * grid[0].length;

        for (int i = 1; i < grid.length - 1; i++) { // i == y
            for (int j = 1; j < grid[i].length - 1; j++) { // j == x
                short curVal = grid[j][i];

                boolean visibleWest = true;
                for (int x = 0; x < j; x++) {
                    short val = grid[x][i];
                    if (curVal <= val) {
                        visibleWest = false;
                        break;
                    }
                }

                boolean visibleEast = true;
                for (int x = j + 1; x < grid.length; x++) {
                    short val = grid[x][i];
                    if (curVal <= val) {
                        visibleEast = false;
                        break;
                    }
                }

                boolean visibleNorth = true;
                for (int y = 0; y < i; y++) {
                    short val = grid[j][y];
                    if (curVal <= val) {
                        visibleNorth = false;
                        break;
                    }
                }

                boolean visibleSouth = true;
                for (int y = i + 1; y < grid[i].length; y++) {
                    short val = grid[j][y];
                    if (curVal <= val) {
                        visibleSouth = false;
                        break;
                    }
                }

                boolean visible = visibleNorth || visibleEast || visibleSouth || visibleWest;
                if (!visible) {
                    log.info("Invisible: [{},{}]", j, i);
                    totalVisible--;
                }
            }
        }
        log.info("Total visible: {}", totalVisible);
    }

    @Test
    void partTwo() {
        List<String> lines = ResourceReader.readResource("/input/day8/input.txt");
        short[][] grid = loadGrid(lines);

        if (grid == null) {
            throw new IllegalStateException("Grid should be loaded");
        }

        Pair<Pair<Integer, Integer>, Integer> highest = new Pair<Pair<Integer, Integer>, Integer>(new Pair(-1, -1), 0);

        for (int i = 1; i < grid.length - 1; i++) { // i == y
            for (int j = 1; j < grid[i].length - 1; j++) { // j == x
                short curVal = grid[j][i];

                int distanceWest = 0;
                for (int x = j - 1; x >= 0; x--) {
                    distanceWest++;
                    short val = grid[x][i];
                    if (curVal <= val) {
                        break;
                    }
                }

                int distanceEast = 0;
                for (int x = j + 1; x < grid.length; x++) {
                    distanceEast++;
                    short val = grid[x][i];
                    if (curVal <= val) {
                        break;
                    }
                }

                int distanceNorth = 0;
                for (int y = i - 1; y >= 0; y--) {
                    distanceNorth++;
                    short val = grid[j][y];
                    if (curVal <= val) {
                        break;
                    }
                }

                int distanceSouth = 0;
                for (int y = i + 1; y < grid[i].length; y++) {
                    distanceSouth++;
                    short val = grid[j][y];
                    if (curVal <= val) {
                        break;
                    }
                }

                int score = distanceNorth * distanceEast * distanceSouth * distanceWest;
                if (score > highest.getValue1()) {
                    highest = new Pair<Pair<Integer, Integer>, Integer>(new Pair(j, i), score);
                }
            }
        }

        log.info("High score: " + highest);
    }

    private short[][] loadGrid(List<String> lines) {
        short[][] grid = null;

        // build the grid of trees
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (grid == null) {
                grid = new short[line.length()][lines.size()];
            }
            for (int j = 0; j < line.length(); j++) {
                grid[j][i] = Short.parseShort(String.valueOf(line.charAt(j)));
            }
        }

        return grid;
    }
}

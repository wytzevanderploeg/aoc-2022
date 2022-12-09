package com.famvdploeg.aoc.day06;

import com.famvdploeg.aoc.AbstractBaseTest;
import com.famvdploeg.aoc.util.ResourceReader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
public class Day06 extends AbstractBaseTest {

    @Test
    void partOne() {
        this.findMarker(4);
    }

    @Test
    void partTwo() {
        this.findMarker(14);
    }

    private void findMarker(int markerLength) {
        List<String> lines = ResourceReader.readResource("/input/day06/input.txt");
        int total = 0;
        for (String line : lines) {
            for (int i = 0; i < line.length() - markerLength; i++) {
                String part = line.substring(i, i + markerLength);
                long count = part.chars()
                        .distinct()
                        .count();
                if (count == markerLength) {
                    log.debug("Marker: {}", i + markerLength);
                    total += (i + markerLength);
                    break;
                }
            }
        }
        log.info("Total: {}", total);
    }
}

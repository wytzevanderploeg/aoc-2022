package com.famvdploeg.aoc.util;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class GridTest {

    /*
        [0, 1, 2]
        [3, 4, 5]
        [6, 7, 8]
    */
    private final Grid<Integer> grid = instantiate();

    @Test
    void flattenByRow() {
        // execute
        Integer[] result = grid.flattenByRow();

        // verify
        Assertions.assertThat(result[1])
                .isEqualTo(1);
        Assertions.assertThat(result[2])
                .isEqualTo(2);
        Assertions.assertThat(result[8])
                .isEqualTo(8);
    }

    @Test
    void flattenByColumn() {
        // execute
        Integer[] result = grid.flattenByColumn();

        // verify
        Assertions.assertThat(result[1])
                .isEqualTo(3);
        Assertions.assertThat(result[2])
                .isEqualTo(6);
        Assertions.assertThat(result[8])
                .isEqualTo(8);
    }

    private Grid<Integer> instantiate() {
        Grid<Integer> grid = new Grid<>(3, 3, Integer.class);
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                grid.set(x, y, y * 3 + x);
            }
        }
        return grid;
    }
}
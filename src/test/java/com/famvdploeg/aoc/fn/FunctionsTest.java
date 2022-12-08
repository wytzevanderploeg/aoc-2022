package com.famvdploeg.aoc.fn;

import org.assertj.core.api.Assertions;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FunctionsTest {

    @Test
    void split() {
        // prepare
        Integer[] values = {1, 2, 3, 4, 5, 6, 7};

        // execute
        Pair<Integer[], Integer[]> result = new Functions()
                .split(values, 3, Integer.class);

        // verify
        Assertions.assertThat(result.getValue0())
                .isEqualTo(new Integer[]{1, 2, 3});
        Assertions.assertThat(result.getValue1())
                .isEqualTo(new Integer[]{4, 5, 6, 7});
    }

    private static Stream<Arguments> splitSource() {
        return Stream.of(
                Arguments.of(0, new Integer[]{}, new Integer[]{2, 3, 4, 5, 6, 7}),
                Arguments.of(1, new Integer[]{1}, new Integer[]{3, 4, 5, 6, 7}),
                Arguments.of(2, new Integer[]{1, 2}, new Integer[]{4, 5, 6, 7}),
                Arguments.of(3, new Integer[]{1, 2, 3}, new Integer[]{5, 6, 7}),
                Arguments.of(4, new Integer[]{1, 2, 3, 4}, new Integer[]{6, 7}),
                Arguments.of(5, new Integer[]{1, 2, 3, 4, 5}, new Integer[]{7}),
                Arguments.of(6, new Integer[]{1, 2, 3, 4, 5, 6}, new Integer[]{})
        );
    }

    @ParameterizedTest
    @MethodSource("splitSource")
    void splitAround(int index, Integer[] first, Integer[] second) {
        // prepare
        Integer[] values = {1, 2, 3, 4, 5, 6, 7};

        // execute
        Pair<Integer[], Integer[]> result = new Functions()
                .splitAround(values, index, Integer.class);

        // verify
        Assertions.assertThat(result.getValue0())
                .isEqualTo(first);
        Assertions.assertThat(result.getValue1())
                .isEqualTo(second);
    }

}
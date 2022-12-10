package com.famvdploeg.aoc.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void distance() {
        // prepare
        Point first = new Point(3, 2);
        Point second = new Point(-3, -2);

        // execute
        Point distance = second.distance(first);

        // verify
        Assertions.assertThat(distance.x())
                .isEqualTo(6);
        Assertions.assertThat(distance.y())
                .isEqualTo(4);
    }
}
package com.famvdploeg.aoc.domain;

import io.reactivex.rxjava3.core.Observable;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public enum Direction {

    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int deltaX;
    private final int deltaY;

    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public Point translate(Point point) {
        return new Point(point.x() + deltaX, point.y() + deltaY);
    }

    public List<Point> translate(Point point, int amount) {
        List<Point> path = Observable
                .range(0, amount)
                .reduceWith(
                        () -> new ArrayList<>(Collections.singletonList(point)),
                        (list, i) -> {
                            Point last = list.get(list.size() - 1);
                            list.add(translate(last));
                            return list;
                        })
                .blockingGet();
        return path.subList(1, path.size());
    }

    public static Direction fromString(String val) {
        return switch (val) {
            case "D" -> DOWN;
            case "U" -> UP;
            case "L" -> LEFT;
            case "R" -> RIGHT;
            default -> Direction.valueOf(val);
        };
    }
}

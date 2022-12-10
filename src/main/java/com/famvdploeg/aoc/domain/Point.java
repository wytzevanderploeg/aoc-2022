package com.famvdploeg.aoc.domain;

public record Point(int x, int y) {

    /**
     * Calculate the distance between this point and the next one.
     * @param other The other point.
     * @return The distance as a point.
     */
    public Point distance(Point other) {
        return new Point(other.x - x, other.y - y);
    }

    public Point follow(Point other) {
        int dX = (x + other.x) / 2;
        int dY = (y + other.y) / 2;
        return new Point(dX, dY);
    }
}

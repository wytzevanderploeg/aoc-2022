package com.famvdploeg.aoc.day09;

import com.famvdploeg.aoc.AbstractBaseTest;
import com.famvdploeg.aoc.domain.Direction;
import com.famvdploeg.aoc.domain.Point;
import com.famvdploeg.aoc.util.ResourceReader;
import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

@Slf4j
public class Day09 extends AbstractBaseTest {

    @Test
    public void uniquePoint() {
        // prepare
        Set<Point> points = new HashSet<>();

        // execute
        points.add(new Point(0, 0));
        points.add(new Point(0, 0));

        // verify
        Assertions.assertThat(points.size())
                .isEqualTo(1);
    }

    @Test
    void partOne() {
        List<String> lines = ResourceReader.readResource("/input/day09/input.txt");
        Point start = new Point(0, 0);
        List<Point> path = new ArrayList<>(Collections.singletonList(start));
        Set<Point> tailPath = new HashSet<>();

        for (String line : lines) {
            String[] op = line.split("\\s+");
            Direction direction = Direction.fromString(op[0]);
            int amount = Integer.parseInt(op[1]);
            log.debug("{}: {}", direction, amount);

            List<Point> section = direction.translate(path.get(path.size() - 1), amount);
            log.debug("Path: {}", section);
            path.addAll(section);
        }

        path.forEach(point -> log.debug("{}", point));

        Point tail = new Point(0, 0);
        Point prevPos = new Point(0, 0);
        for (Point head : path) {
            Point distance = head.distance(tail);
            if (Math.abs(distance.x()) > 1 || Math.abs(distance.y()) > 1) {
                log.info("Move to: {}", distance);
                tail = prevPos;
            }
            tailPath.add(tail);
            prevPos = head;
        }
        log.info("Tail: {}", tailPath);
        log.info("TailPositions: {}", tailPath.size());

        Assertions.assertThat(tailPath.size())
                .describedAs("Solution")
                .isEqualTo(6212);
    }

    @Test
    void partTwo() {
        List<String> lines = ResourceReader.readResource("/input/day09/input.txt");
        Point start = new Point(0, 0);
        List<Point> path = new ArrayList<>(Collections.singletonList(start));

        List<Point> rope = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            rope.add(new Point(0, 0));
        }

        for (String line : lines) {
            String[] op = line.split("\\s+");
            Direction direction = Direction.fromString(op[0]);
            int amount = Integer.parseInt(op[1]);
            log.debug("{}: {}", direction, amount);

            List<Point> section = direction.translate(path.get(path.size() - 1), amount);
            log.debug("Path: {}", section);
            path.addAll(section);
        }

        path.forEach(point -> log.debug("{}", point));

        Set<Point> tailTrail = new HashSet<>();

        for (Point head : path) {
            // Move the head
            rope.set(0, head);

            // Let the body follow like a snake...
            for (int i = 1; i < rope.size(); i ++) {
                Point first = rope.get(i - 1);
                Point second = rope.get(i);

                Point distance = second.distance(first);
                int absX = Math.abs(distance.x());
                int absY = Math.abs(distance.y());
                if (absX < 2 && absY < 2) {
                    continue; // No need to move
                }

                log.debug("Distance: {}:{}", distance.x(), distance.y());

                // if a move is made it will try to align horizontal/vertical.
                int newX = second.x() + Integer.signum(distance.x()) * (int) Math.ceil(absX / 2.0);
                int newY = second.y() + Integer.signum(distance.y()) * (int) Math.ceil(absY / 2.0);
                rope.set(i, new Point(newX, newY));
            }

            if (log.isDebugEnabled()) {
                drawRope(rope);
            }

            // track tail
            tailTrail.add(rope.get(rope.size() - 1));
        }
        log.info("TailTrail: {}", tailTrail.size());

        Assertions.assertThat(tailTrail.size())
                .describedAs("Solution")
                .isEqualTo(2522);
    }

    private void drawRope(List<Point> rope) {
        System.out.println("====================");
        for (int y = -10; y < 10; y++) {
            for (int x = -10; x < 10; x++) {
                Point point = new Point(x, y);
                if (rope.contains(point)) {
                    boolean isHead = rope.get(0).equals(point);
                    System.out.print(isHead ? "@" : rope.indexOf(point));
                } else {
                    System.out.print("-");
                }
            }
            System.out.print(System.lineSeparator());
        }
    }
}

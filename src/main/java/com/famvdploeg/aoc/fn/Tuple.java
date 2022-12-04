package com.famvdploeg.aoc.fn;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class Tuple<T> {

    private final T first;
    private final T second;

    public T get(int index) {
        return switch (index) {
            case 0 -> first;
            case 1 -> second;
            default -> throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        };
    }

}

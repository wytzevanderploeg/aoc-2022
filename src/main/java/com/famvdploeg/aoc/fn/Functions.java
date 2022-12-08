package com.famvdploeg.aoc.fn;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Functions {

    public Set<Character> getIntersection(List<String> values) {
        Stream<Character> head = values.get(0).chars()
                .distinct()
                .mapToObj(i -> (char) i);

        for (String tail : values.subList(1, values.size())) {
            head = head.filter(c ->
                    tail.chars().anyMatch(value -> c == value));
        }

        return head.collect(Collectors.toSet());
    }

    public <T> List<T> getIntersection(List<T> first, List<T> second) {
        return first.stream()
                .filter(second::contains)
                .collect(Collectors.toList());
    }

    public <T> boolean fullMatch(Collection<T> first, Collection<T> second) {
        return first.containsAll(second) || second.containsAll(first);
    }

    public Observable<String> toMatches(Matcher matcher) {
        return Observable.create(emitter -> {
            while (matcher.find()) {
                emitter.onNext(matcher.group(1));
            }
        });
    }

    /**
     * Split an array at the given index.
     * @param input The input to split.
     * @param index The index to split at. Zero based.
     * @param arrayType The required type.
     * @return A pair containing the two arrays.
     * @param <T> The array type.
     */
    public <T> Pair<T[], T[]> split(T[] input, int index, Class<T> arrayType) {
        T[] first = this.instantiateArray(index, arrayType);
        T[] second = this.instantiateArray(input.length - index, arrayType);
        System.arraycopy(input, 0, first, 0, index);
        System.arraycopy(input, index, second, 0, input.length - index);
        return new Pair<>(first, second);
    }

    public <T> Pair<T[], T[]> splitAround(T[] input, int index, Class<T> arrayType) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null.");
        }
        if (index < 0 || index >= input.length) {
            throw new IndexOutOfBoundsException(
                    String.format("Index out of bounds: %s for input with size: %s", index, input.length));
        }

        T[] first = this.instantiateArray(index, arrayType);
        System.arraycopy(input, 0, first, 0, index);

        int right = index + 1;
        int sizeRight = input.length - right;
        T[] second = this.instantiateArray(sizeRight, arrayType);
        System.arraycopy(input, right, second, 0, sizeRight);

        return new Pair<>(first, second);
    }

    public Stream<Character> toCharStream(String input) {
        return input.chars()
                .mapToObj(c -> (char) c);
    }

    public String toString(Collection<Character> characters) {
        return characters.stream()
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    @SuppressWarnings("unchecked")
    private <T> T[] instantiateArray(int size, Class<T> arrayType) {
        return (T[]) Array.newInstance(arrayType, size);
    }

    @SuppressWarnings("unchecked")
    private <T> T[][] instantiateArray(int width, int height, Class<T> arrayType) {
        return (T[][]) Array.newInstance(arrayType, width, height);
    }
}

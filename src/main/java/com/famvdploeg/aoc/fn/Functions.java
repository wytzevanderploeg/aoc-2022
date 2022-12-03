package com.famvdploeg.aoc.fn;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Stream<Character> toCharStream(String input) {
        return input.chars()
                .mapToObj(c -> (char) c);
    }

    public String toString(Collection<Character> characters) {
        return characters.stream()
                .map(Object::toString)
                .collect(Collectors.joining());
    }

}

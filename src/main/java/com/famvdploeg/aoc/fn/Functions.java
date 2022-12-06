package com.famvdploeg.aoc.fn;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

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

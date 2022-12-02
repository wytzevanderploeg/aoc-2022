package com.famvdploeg.aoc.domain;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(onlyExplicitlyIncluded = true)
public class Elf {

    @ToString.Include
    private final String name;

    private final List<Food> bagOfFood = new ArrayList<>();

    public void addFood(Food food) {
        this.bagOfFood.add(food);
    }

    @ToString.Include
    public int getTotalCalories() {
        return this.bagOfFood.stream()
                .map(Food::getCalories)
                .reduce(0, Integer::sum);
    }

}

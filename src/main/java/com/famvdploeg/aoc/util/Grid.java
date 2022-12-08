package com.famvdploeg.aoc.util;

import java.lang.reflect.Array;

public class Grid<T> {

    private final Class<T> arrayType;
    private final T[][] grid;

    public Grid(int width, int height, Class<T> arrayType) {
        this.arrayType = arrayType;
        this.grid = this.instantiateArray(width, height);
    }

    public Grid(T[][] grid, Class<T> arrayType) {
        this.arrayType = arrayType;
        this.grid = grid;
    }

    public T get(int x, int y) {
        return this.grid[x][y];
    }

    public void set(int x, int y, T value) {
        this.grid[x][y] = value;
    }

    public int getWidth() {
        return this.grid.length;
    }

    public int getHeight() {
        return this.grid[0].length;
    }

    /**
     * Get items on the x-axis.
     * @param y The y value.
     * @return All the items in the row.
     */
    public T[] getRow(int y) {
        T[] result = this.instantiateArray(getWidth());
        for (int x = 0; x < getWidth(); x++) {
            result[x] = get(x, y);
        }
        return result;
    }

    public T[] getColumn(int x) {
        T[] result = this.instantiateArray(getHeight());
        for (int y = 0; y < getHeight(); y++) {
            result[y] = get(x, y);
        }
        return result;
    }

    /**
     * Flattens the grid and returns it as a byte array.
     * @return A flattened array in which all rows are concatenated.
     */
    public T[] flattenByRow() {
        T[] result = this.instantiateArray(getWidth() * getHeight());
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                result[y * getWidth() + x] = get(x, y);
            }
        }
        return result;
    }

    /**
     * Flattens the grid and returns it as a byte array.
     * @return A flattened array in which all columns are concatenated.
     */
    public T[] flattenByColumn() {
        T[] result = this.instantiateArray(getWidth() * getHeight());
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                result[x * getHeight() + y] = get(x, y);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private T[] instantiateArray(int size) {
        return (T[]) Array.newInstance(arrayType, size);
    }

    @SuppressWarnings("unchecked")
    private T[][] instantiateArray(int width, int height) {
        return (T[][]) Array.newInstance(arrayType, width, height);
    }

}

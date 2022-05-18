package com.rdc.triplets;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TripletsServiceImplTest {

    @Test
    void calculate_twoSets() {
        final var numbers = Stream.of(-1, 0, 1, 2, -1, -4).toList();

        final var tripletsService = new TripletsServiceImpl();
        final var actual = tripletsService.calculate(new TripletsInput(numbers));
        assertEquals(2, actual.size());

        assertTrue(actual.stream().anyMatch(v -> v.containsAll(List.of(-1, 0, 1))));
        assertTrue(actual.stream().anyMatch(v -> v.containsAll(List.of(-1, -1, 2))));
    }

    @Test
    void calculate_fourSets() {
        final var numbers = Stream.of(-1, 0, 1, 2, -1, -4, 2, 5).toList();

        final var tripletsService = new TripletsServiceImpl();
        final var actual = tripletsService.calculate(new TripletsInput(numbers));
        assertEquals(4, actual.size());

        assertTrue(actual.stream().anyMatch(v -> v.containsAll(List.of(-1, 0, 1))));
        assertTrue(actual.stream().anyMatch(v -> v.containsAll(List.of(-1, -1, 2))));
        assertTrue(actual.stream().anyMatch(v -> v.containsAll(List.of(-4, 2, 2))));
        assertTrue(actual.stream().anyMatch(v -> v.containsAll(List.of(-4, -1, 5))));
    }

    @Test
    void calculate_noSets() {
        final var numbers = Stream.of(-1, 0, 5, 7, -1, -1).toList();

        final var tripletsService = new TripletsServiceImpl();
        final var actual = tripletsService.calculate(new TripletsInput(numbers));
        assertEquals(0, actual.size());
    }

    @Test
    void calculate_allZeroes() {
        final var numbers = Stream.of(0, 0, 0, 0, 0, 0).toList();

        final var tripletsService = new TripletsServiceImpl();
        final var actual = tripletsService.calculate(new TripletsInput(numbers));
        assertEquals(1, actual.size());

        assertTrue(actual.stream().anyMatch(v -> v.containsAll(List.of(0, 0, 0))));
    }

    @Test
    void calculate_withLessThanThree() {
        final var numbers = Stream.of(1, 1).toList();

        final var tripletsService = new TripletsServiceImpl();
        final var actual = tripletsService.calculate(new TripletsInput(numbers));
        assertEquals(0, actual.size());
    }
}
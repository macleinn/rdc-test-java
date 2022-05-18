package com.rdc.triplets;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TripletsServiceImpl implements TripletsService {
    @Override
    public Set<List<Integer>> calculate(TripletsInput input) {
        // Sort the numbers first.
        // An ordered list of numbers lets us expect a higher sum as the index move to the right.
        final var sorted = input.numbers().stream().sorted(Comparator.comparingInt(v -> v)).toList();

        final var results = new HashSet<List<Integer>>();

        // Instead of using three loops to check the sum of each combination, we will use 2 loops.
        // The outer loop will move the index of the first number.
        // In the inner loop, we will:
        // 1. move the second index to the right if we want the sum to increase
        // 2. move the third index to the right if we want the sum to decrease
        for (int i = 0; i < sorted.size(); i++) {
            // Index of the second number
            int j = i + 1;
            // Index of the third number
            int k = sorted.size() - 1;

            // Move the indices and terminate before they meet
            while (j < k) {
                final var sum = sorted.get(i) + sorted.get(j) + sorted.get(k);
                if (sum < 0) {
                    j++;
                } else if (sum > 0) {
                    k--;
                } else {
                    results.add(List.of(sorted.get(i), sorted.get(j), sorted.get(k)));
                    j++;
                    k--;
                }
            }
        }

        return results;
    }
}

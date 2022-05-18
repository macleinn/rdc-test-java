package com.rdc.triplets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TripletsController {

    @Autowired
    private TripletsService tripletsService;

    /**
     * This takes a list of numbers in the request body and find unique triplets that sum to zero.
     */
    @PostMapping("/triplets")
    public ResponseEntity<String> findTriplets(@RequestBody TripletsInput input) {
        final var sets = tripletsService.calculate(input);
        final var resp = toString(sets);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    private String toString(Collection<List<Integer>> sets) {
        final List<String> tripletStr = sets.stream()
                .map(v -> "[" + v.stream().map(Object::toString).collect(Collectors.joining(",")) + "]")
                .toList();
        return "[" + String.join(",", tripletStr) + "]";
    }

}

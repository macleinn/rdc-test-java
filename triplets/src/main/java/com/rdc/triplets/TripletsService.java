package com.rdc.triplets;

import java.util.List;
import java.util.Set;

public interface TripletsService {

    /**
     * Finds the unique triplets that sum up to zero.
     */
    Set<List<Integer>> calculate(TripletsInput input);

}

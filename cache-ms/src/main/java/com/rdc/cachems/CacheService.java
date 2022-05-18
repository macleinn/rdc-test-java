package com.rdc.cachems;

import java.util.Optional;

public interface CacheService {

    /**
     * Fetches the cached value of a given key
     */
    Optional<String> get(String key);

    /**
     * Creates the given key and value.
     */
    Optional<CacheProp> store(CachePropRequest request);

    /**
     * Updates the given key and value.
     */
    Optional<CacheProp> update(String key, CacheUpdate update);

    /**
     * Deletes the given key
     */
    void delete(String key);
}

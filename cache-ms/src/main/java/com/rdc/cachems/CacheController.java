package com.rdc.cachems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CacheController {

    @Autowired
    private CacheService cacheService;

    /**
     * Fetches a cached property by given key
     */
    @GetMapping("/values/{key}")
    public ResponseEntity<String> getByKey(@PathVariable("key") String key) {
        final var value = cacheService.get(key);
        return value
                .map(v -> new ResponseEntity<>(v, HttpStatus.OK))
                .orElseThrow(() -> new IllegalArgumentException("Failed to find by key: " + key));
    }

    /**
     * Creates given key and value
     */
    @PostMapping(path = "/values",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody CachePropRequest property) {
        final var stored = cacheService.store(property);
        return stored
                .map(v -> new ResponseEntity<>(v.value(), HttpStatus.CREATED))
                .orElseThrow(() -> new IllegalArgumentException("Failed to create " + property.key() + ":" + property.value()));
    }

    /**
     * Updates given key and value
     */
    @PutMapping("/values/{key}")
    public ResponseEntity<String> update(@PathVariable("key") String key, @RequestBody CacheUpdate update) {
        final var stored = cacheService.update(key, update);
        return stored
                .map(v -> new ResponseEntity<>(v.value(), HttpStatus.OK))
                .orElseThrow(() -> new IllegalArgumentException("Failed to update " + key));
    }

    /**
     * Expires given key
     */
    @DeleteMapping("/values/{key}")
    public ResponseEntity<Void> expire(@PathVariable("key") String key) {
        cacheService.delete(key);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package com.rdc.cachems;

/**
 * This stores the cached value and its expiry time in seconds.
 */
public record CacheProp(String value, long expirySeconds) {
}

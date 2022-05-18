package com.rdc.cachems;

import org.springframework.lang.Nullable;

/**
 * This represents a request to store a key-value pair and its expiry time in minutes.
 */
public record CachePropRequest(String key, String value, @Nullable Integer expiryInMin) {
}

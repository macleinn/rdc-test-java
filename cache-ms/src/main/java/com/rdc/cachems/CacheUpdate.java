package com.rdc.cachems;

import org.springframework.lang.Nullable;

/**
 * This is an update request for a cached value and its expiry time in minutes.
 */
public record CacheUpdate(String value, @Nullable Integer expiryInMin) {
}

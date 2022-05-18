package com.rdc.cachems;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CacheServiceImplTest {

    @Test
    void store_1millionInSingleThread() {
        final CacheService cacheService = new CacheServiceImpl();
        runCreate(cacheService, 1000000);

        System.out.println(Runtime.getRuntime().totalMemory());
        System.out.println(Runtime.getRuntime().freeMemory());

        assertEquals("1", cacheService.get("1").orElseThrow());
        assertEquals("100", cacheService.get("100").orElseThrow());
    }

    @Test
    void store_10000In1000Threads() {
        final int n = 1000;
        final var executorService = Executors.newFixedThreadPool(n);
        final CacheService cacheService = new CacheServiceImpl();

        try {
            for (int i = 0; i < 10; i++) {
                executorService.execute(() -> runCreate(cacheService, 10000));
            }
        } finally {
            executorService.shutdown();
        }

        try {
            assertTrue(executorService.awaitTermination(1, TimeUnit.MINUTES));
        } catch (InterruptedException e) {
            Assertions.fail();
        }

        System.out.println(Runtime.getRuntime().totalMemory());
        System.out.println(Runtime.getRuntime().freeMemory());

        assertEquals("1", cacheService.get("1").orElseThrow());
        assertEquals("100", cacheService.get("100").orElseThrow());
        assertEquals("1000", cacheService.get("1000").orElseThrow());
        assertEquals("9999", cacheService.get("9999").orElseThrow());
        assertTrue(cacheService.get("10000").isEmpty());
    }

    @Test
    void delete_afterStore() {
        final CacheService cacheService = new CacheServiceImpl();
        runCreate(cacheService, 10);
        assertEquals("1", cacheService.get("1").orElseThrow());

        cacheService.delete("1");

        final var actual = cacheService.get("1");
        assertTrue(actual.isEmpty());
    }

    private void runCreate(CacheService cacheService, int n) {
        for (int i = 0; i < n; i++) {
            final var item = "" + i;
            final var request = new CachePropRequest(item, item, null);
            assertTrue(cacheService.store(request).isPresent());
        }
    }
}
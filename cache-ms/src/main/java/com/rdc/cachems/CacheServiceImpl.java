package com.rdc.cachems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
class CacheServiceImpl implements CacheService {
    private static final Logger log = LoggerFactory.getLogger(CacheServiceImpl.class);

    private static final Map<String, CacheProp> cacheMap = new ConcurrentHashMap<>();

    @Override
    public Optional<String> get(String key) {
        log.info("Get cached value by key: {}", key);

        final Optional<String> resp;
        if (cacheMap.containsKey(key)) {
            final var val = cacheMap.get(key);
            if (Instant.now().getEpochSecond() < val.expirySeconds()) {
                resp = Optional.of(val.value());
            } else {
                // expired so remove from cache
                cacheMap.remove(key);
                resp = Optional.empty();
            }
        } else {
            resp = Optional.empty();
        }
        return resp;
    }

    @Override
    public Optional<CacheProp> store(CachePropRequest request) {
        log.debug("Create ({}:{}) with expiry in {} min", request.key(), request.value(), request.expiryInMin());
        final var duration = Optional.ofNullable(request.expiryInMin()).orElse(10);
        final var prop = new CacheProp(request.value(), Instant.now().plus(duration, ChronoUnit.MINUTES).getEpochSecond());
        cacheMap.put(request.key(), prop);
        return Optional.of(prop);
    }

    @Override
    public Optional<CacheProp> update(String key, CacheUpdate update) {
        log.info("Update ({}:{}) with expiry in {} min", key, update.value(), update.expiryInMin());

        final CacheProp prop;
        if (cacheMap.containsKey(key)) {
            final var duration = Optional.ofNullable(update.expiryInMin()).orElse(10);
            prop = new CacheProp(update.value(), Instant.now().plus(duration, ChronoUnit.MINUTES).getEpochSecond());
            cacheMap.put(key, prop);
        } else {
            prop = null;
        }

        return Optional.ofNullable(prop);
    }

    @Override
    public void delete(String key) {
        log.info("Delete key: {}", key);
        cacheMap.remove(key);
    }
}

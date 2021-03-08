package com.codetc.common.service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisService {
    boolean set(String key, Object value);

    boolean set(String key, Object value, Long expireTime);

    <T> T get(String key);

    boolean exists(String key);

    void remove(String key);

    long increment(String key, long delta);

    Set<String> keys(String pattern);

    void expire(String key, Long timeout);

    void expire(String key, Long timeout, TimeUnit unit);
}

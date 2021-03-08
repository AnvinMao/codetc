package com.codetc.common.service;

import com.codetc.common.model.RedisLock;

/**
 * Redis分布式锁
 * Created by anvin on 1/15/2021.
 */
public interface RedisMutexLock {

    RedisLock lock(String key);

    RedisLock lock(String key, long expire);

    RedisLock lock(String key, int timeout);

    RedisLock lock(String key, int timeout, long expire);

    boolean releaseLock(RedisLock lock);
}

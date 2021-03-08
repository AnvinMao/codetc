package com.codetc.common.service.impl;

import cn.hutool.core.lang.UUID;
import com.codetc.common.model.RedisLock;
import com.codetc.common.service.RedisMutexLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by anvin on 1/15/2021.
 */
@Slf4j
@Service
public class RedisMutexLockLockImpl implements RedisMutexLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 锁获取超时时间
    private final int TIMEOUT = 0;

    // 锁默认失效时间
    private final long EXPIRE_MILLS = 10000;

    // 获取锁间隔
    private final long SLEEP_MILLS = 5000;

    private final String KEY_PREFIX = "LOCK:";

    private static final String UNLOCK_LUA;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }

    @Override
    public RedisLock lock(String key) {
        return this.lock(key, EXPIRE_MILLS);
    }

    @Override
    public RedisLock lock(String key, long expire) {
        return this.lock(key, TIMEOUT, expire);
    }

    @Override
    public RedisLock lock(String key, int timeout) {
        return this.lock(key, timeout, EXPIRE_MILLS);
    }

    @Override
    public RedisLock lock(String key, int timeout, long expire) {
        long startTime = System.currentTimeMillis();
        RedisLock lock = null;
        String uuid = UUID.randomUUID().toString();
        key = KEY_PREFIX + key;

        do {
            try {
                Boolean acquired = this.redisTemplate.opsForValue().setIfAbsent(key, uuid, expire, TimeUnit.MILLISECONDS);
                if (acquired != null && acquired) {
                    lock = new RedisLock(key, uuid);
                    break;
                }

                if (timeout == 0) {
                    break;
                }

                Thread.sleep(SLEEP_MILLS);
            } catch (InterruptedException e) {
                log.error("error for get redis lock:", e);
                return null;
            }

        } while (System.currentTimeMillis() < startTime + timeout * 1000);

        return lock;
    }

    @Override
    public boolean releaseLock(RedisLock lock) {
        List<String> keys = Collections.singletonList(lock.getKey());

        Long result = this.redisTemplate.execute(
                new DefaultRedisScript<>(UNLOCK_LUA, Long.class), keys, lock.getValue());
        return result != null && result > 0;
    }
}

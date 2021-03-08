package com.codetc.common.service.impl;

import com.codetc.common.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 业务类
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean set(final String key, Object value) {
        try {
            this.redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean set(final String key, Object value, Long expireTime) {
        try {
            this.redisTemplate.opsForValue().set(key, value);
            this.redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(final String key) {
        if (key == null) {
            return null;
        }

        return (T)this.redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean exists(final String key) {
        Boolean exists = this.redisTemplate.hasKey(key);
        return exists != null && exists;
    }

    @Override
    public void remove(final String key) {
        if (this.exists(key)) {
            this.redisTemplate.delete(key);
        }
    }

    @Override
    public long increment(final String key, long delta) {
        if (delta <= 0) {
            delta = 1;
        }

        Long num = this.redisTemplate.opsForValue().increment(key, delta);
        return num == null ? 1L : num;
    }

    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    @Override
    public void expire(String key, Long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    @Override
    public void expire(String key, Long timeout, TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }
}

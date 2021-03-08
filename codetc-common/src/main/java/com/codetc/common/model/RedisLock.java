package com.codetc.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Redis 锁
 * Created by anvin on 1/15/2021.
 */
@Data
@AllArgsConstructor
public class RedisLock implements Serializable {
    private String key;
    private String value;
}

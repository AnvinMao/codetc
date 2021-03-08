package com.codetc.common.constant;

/**
 * Redis Key 枚举类
 */
public enum RedisKeyEnum {
    /**
     * AUTH USER JWT ACCESS 格式
     * auth:user:jwt:access:{username}
     */
    AUTH_USER_JWT_ACCESS_FORMAT("auth:user:jwt:access:{}");

    private final String key;

    RedisKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}

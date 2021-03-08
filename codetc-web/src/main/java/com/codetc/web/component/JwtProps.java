package com.codetc.web.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 配置参数类
 */
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProps {
    /**
     * JWT 加解密使用的密钥
     */
    public static String secret;
    /**
     * JWT 存储的请求头
     */
    public static String header;
    /**
     * JWT AccessToken 过期时间
     */
    public static Long accessTokenExpireTime;

    public void setSecret(String secret) {
        JwtProps.secret = secret;
    }

    public void setHeader(String header) {
        JwtProps.header = header;
    }

    public void setAccessTokenExpireTime(Long expireTime) {
        JwtProps.accessTokenExpireTime = expireTime;
    }
}

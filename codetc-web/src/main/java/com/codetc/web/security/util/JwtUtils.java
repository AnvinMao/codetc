package com.codetc.web.security.util;

import com.codetc.common.constant.ResultCodeEnum;
import com.codetc.web.component.JwtProps;
import com.codetc.web.security.entity.JwtUserDetails;
import com.codetc.common.exception.AuthException;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 */
public class JwtUtils {
    /**
     * 生成 AccessToken
     */
    public static String generateAccessToken(JwtUserDetails jwtUserDetails) {
        Map<String, Object> claims = new HashMap<>(5);
        claims.put("sub", jwtUserDetails.getUsername());
        return generateToken(claims, JwtProps.accessTokenExpireTime);
    }

    /**
     * 生成 Token
     */
    public static String generateToken(Map<String, Object> claims, Long expiration) {
        // 生成JWT
        return Jwts.builder()
                .setClaims(claims)
                // 签发时间
                .setIssuedAt(new Date())
                // 失效时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                // 签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, JwtProps.secret)
                .compact();
    }

    /**
     * 解析 Token
     */
    public static Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(JwtProps.secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new AuthException(ResultCodeEnum.INVALID.getCode(), "Token 过期");
        } catch (JwtException e) {
            throw new AuthException(ResultCodeEnum.INVALID.getCode(), "Token 无效");
        }
    }

    /**
     * 获取 Token 中的用户名
     */
    public static String getUsername(String token) {
        return getClaims(token).getSubject();
    }
}

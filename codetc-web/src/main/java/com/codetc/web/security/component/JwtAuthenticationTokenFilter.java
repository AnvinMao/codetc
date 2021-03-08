package com.codetc.web.security.component;

import cn.hutool.core.util.StrUtil;
import com.codetc.common.constant.RedisKeyEnum;
import com.codetc.web.component.IgnoreUrls;
import com.codetc.web.component.JwtProps;
import com.codetc.web.security.entity.JwtUserDetails;
import com.codetc.web.security.util.JwtUtils;
import com.codetc.common.exception.AuthException;
import com.codetc.common.service.RedisService;
import com.codetc.common.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 认证过滤器
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    /**
     * Redis 业务类
     */
    @Autowired
    private RedisService redisService;

    @Autowired
    private IgnoreUrls ignoreUrls;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(JwtProps.header);
        if (!StrUtil.isEmpty(token)) {
            String username;
            try {
                username = JwtUtils.getUsername(token);
            } catch (AuthException e) {
                ServletUtils.writeJson(response, e.getErrCode(), e.getErrMsg());
                return;
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                String redisJwtKey = StrUtil.format(RedisKeyEnum.AUTH_USER_JWT_ACCESS_FORMAT.getKey(), username);
                JwtUserDetails details = this.redisService.get(redisJwtKey);
                if (details != null && details.isEnabled()) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            details,
                            null,
                            details.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathRequestMatcher matcher;
        for (String url : this.ignoreUrls.getUrls()) {
            matcher = new AntPathRequestMatcher(url);
            if (matcher.matches(request)) {
                return true;
            }
        }

        return false;
    }
}

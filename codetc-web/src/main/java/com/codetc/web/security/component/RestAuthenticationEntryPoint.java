package com.codetc.web.security.component;

import com.codetc.common.constant.ResultCodeEnum;
import com.codetc.common.util.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未登录处理类
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ServletUtils.writeJson(
                response,
                ResultCodeEnum.UNAUTHORIZED.getCode(),
                ResultCodeEnum.UNAUTHORIZED.getMessage());
    }
}

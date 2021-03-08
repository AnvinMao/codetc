package com.codetc.web.security.component;

import com.codetc.common.constant.ResultCodeEnum;
import com.codetc.common.util.ServletUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 无权限处理类
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ServletUtils.writeJson(
                response,
                ResultCodeEnum.FORBIDDEN.getCode(),
                ResultCodeEnum.FORBIDDEN.getMessage());
    }
}

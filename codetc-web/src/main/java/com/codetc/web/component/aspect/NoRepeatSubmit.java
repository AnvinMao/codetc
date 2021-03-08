package com.codetc.web.component.aspect;

import cn.hutool.crypto.SecureUtil;
import com.codetc.common.component.aspect.RepeatSubmitAspect;
import com.codetc.common.util.ServletUtils;
import com.codetc.web.security.util.SecurityUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 重复提交检测
 * Created by anvin on 1/16/2021.
 */
@Component
public class NoRepeatSubmit extends RepeatSubmitAspect {

    @Override
    protected String getKey() {
        StringBuilder sb = new StringBuilder();
        HttpServletRequest request = ServletUtils.getRequest();
        sb.append(request.getServletPath());
        Long userId = this.getCurrentUserId();
        if (userId == null) {
            sb.append(ServletUtils.getIpAddress());
        } else {
            sb.append(userId);
        }

        return SecureUtil.md5(sb.toString());
    }

    /**
     * 获取当前用户 ID
     */
    private Long getCurrentUserId() {
        try {
            return SecurityUtils.getCurrentUserId();
        } catch (Exception e) {
            return null;
        }
    }
}

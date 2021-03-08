package com.codetc.web.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth 配置参数类
 */
@Data
@ConfigurationProperties(prefix = "auth.ignored")
public class IgnoreUrls {
    /**
     * 忽略认证的接口
     */
    private List<String> urls = new ArrayList<>();
}

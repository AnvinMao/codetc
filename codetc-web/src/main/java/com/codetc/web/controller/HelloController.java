package com.codetc.web.controller;

import com.codetc.common.model.api.CommonResult;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by anvin on 1/15/2021.
 */
@Api(tags = "Hello")
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/havePermission")
    @PreAuthorize("hasAnyAuthority('user:read')")
    public CommonResult<Object> permission() {
        return CommonResult.success("I have permission.");
    }

    @GetMapping("/noPermission")
    @PreAuthorize("hasAnyAuthority('admin:read')")
    public CommonResult<Object> noPermission() {
        return CommonResult.success("no permission.");
    }
}

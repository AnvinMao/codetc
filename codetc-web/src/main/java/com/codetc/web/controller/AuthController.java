package com.codetc.web.controller;

import com.codetc.common.annotation.NoRepeatSubmit;
import com.codetc.common.annotation.OperationLog;
import com.codetc.web.model.param.LoginParam;
import com.codetc.web.model.param.UserParam;
import com.codetc.web.model.vo.TokenVO;
import com.codetc.common.model.api.CommonResult;
import com.codetc.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "Auth 授权")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @ApiOperation("用户注册")
    @PostMapping("/register")
    @OperationLog("注册")
    public CommonResult<Object> register(@RequestBody @Valid UserParam param) {
        this.userService.createUser(param);
        return CommonResult.success();
    }

    @ApiOperation("用户登陆")
    @PostMapping("/login")
    @OperationLog("用户登录")
    @NoRepeatSubmit
    public CommonResult<TokenVO> login(@RequestBody @Valid LoginParam loginParam) {
        TokenVO vo = this.userService.login(loginParam);
        return CommonResult.success(vo);
    }

    @ApiOperation("用户登出")
    @PostMapping("/logout")
    public CommonResult<Object> logout() {
        this.userService.logout();
        return CommonResult.success();
    }

    @ApiOperation("刷新Token")
    @PostMapping("/refresh")
    public CommonResult<TokenVO> refresh() {
        TokenVO vo = this.userService.refresh();
        return CommonResult.success(vo);
    }
}

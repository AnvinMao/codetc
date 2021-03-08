package com.codetc.common.component.handler;

import com.codetc.common.constant.ResultCodeEnum;
import com.codetc.common.exception.ApiException;
import com.codetc.common.model.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理类
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 全局异常 统一JSON格式返回
     */
    @ExceptionHandler(Exception.class)
    public CommonResult<Object> exception(Exception e) {
        log.error("服务器未知异常", e);
        return CommonResult.failed(e.getMessage());
    }

    /**
     * Spring Security 未登录异常
     */
    @ExceptionHandler(AuthenticationException.class)
    public CommonResult<Object> authenticationException(AuthenticationException e) {
        return CommonResult.failed(ResultCodeEnum.UNAUTHORIZED.getCode(), ResultCodeEnum.UNAUTHORIZED.getMessage());
    }

    /**
     * Spring Security 无权限异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public CommonResult<Object> accessDeniedException(AccessDeniedException e) {
        return CommonResult.failed(ResultCodeEnum.FORBIDDEN.getCode(), ResultCodeEnum.FORBIDDEN.getMessage());
    }

    /**
     * Spring Validation 校验异常
     */
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public CommonResult<Object> validationException(Exception e) {
        BindingResult bindingResult;
        if (e instanceof BindException) {
            bindingResult = ((BindException) e).getBindingResult();
        } else {
            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        }
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder errorMsg = new StringBuilder("校验异常:");
        for (FieldError error : fieldErrors) {
            errorMsg.append(error.getField()).append("-").append(error.getDefaultMessage()).append(",");
        }
        errorMsg.deleteCharAt(errorMsg.length() - 1);
        return CommonResult.failed(ResultCodeEnum.VALID_FAIL.getCode(), errorMsg.toString());
    }

    /**
     * 自定义 API接口异常
     */
    @ExceptionHandler(ApiException.class)
    public CommonResult<Object> apiException(ApiException e) {
        return CommonResult.failed(e.getErrCode(), e.getErrMsg());
    }
}

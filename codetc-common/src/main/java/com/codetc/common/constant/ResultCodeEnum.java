package com.codetc.common.constant;

/**
 * API 返回状态码
 */
public enum ResultCodeEnum {
    /**
     * 成功
     */
    SUCCESS(0, "ok"),
    /**
     * 参数检验失败
     */
    VALID_FAIL(2001, "传入参数错误"),
    /**
     * 重复提交
     */
    REPEAT_SUBMIT(3001, "请勿重复提交"),
    /**
     * 未登录
     */
    UNAUTHORIZED(4001, "未登录"),
    /**
     * 无权限
     */
    FORBIDDEN(4002, "无权限"),
    /**
     * 未认证
     */
    INVALID(4003, "未认证"),
    /**
     * 服务器异常
     */
    FAILED(5000, "服务器异常");
    private final Integer code;
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

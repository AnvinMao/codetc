package com.codetc.common.model.api;

import com.codetc.common.constant.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * API 返回格式对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> CommonResult<T> success() {
        return new CommonResult<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), null);
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), data);
    }

    public static <T> CommonResult<CommonPage<T>> success(List<T> list) {
        CommonPage<T> commonPage = new CommonPage<>();
        PageInfo<T> pageInfo = new PageInfo<>(list);
        commonPage.setPageIndex(pageInfo.getPageNum());
        commonPage.setPageSize(pageInfo.getPageSize());
        commonPage.setPages(pageInfo.getPages());
        commonPage.setTotal(pageInfo.getTotal());
        commonPage.setItems(pageInfo.getList());
        return new CommonResult<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), commonPage);
    }

    public static <T> CommonResult<T> failed() {
        return new CommonResult<>(ResultCodeEnum.FAILED.getCode(), ResultCodeEnum.FAILED.getMessage(), null);
    }

    public static <T> CommonResult<T> failed(String msg) {
        return new CommonResult<>(ResultCodeEnum.FAILED.getCode(), msg, null);
    }

    public static <T> CommonResult<T> failed(Integer code, String msg) {
        return new CommonResult<>(code, msg, null);
    }
}

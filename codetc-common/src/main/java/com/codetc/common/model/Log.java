package com.codetc.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by anvin on 1/16/2021.
 */
@Data
public class Log implements Serializable {

    /**
     * 操作类型
     */
    private String operation;

    /**
     * 消耗时间(ms)
     */
    private Integer spanTime;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 操作IP
     */
    private String ip;
}

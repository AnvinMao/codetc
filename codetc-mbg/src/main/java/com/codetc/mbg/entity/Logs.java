package com.codetc.mbg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Logs implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作用户
     */
    private Long userId;

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

    /**
     * 操作时间
     */
    private LocalDateTime insertTime;
}

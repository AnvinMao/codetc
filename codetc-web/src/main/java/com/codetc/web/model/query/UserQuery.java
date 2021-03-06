package com.codetc.web.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("用户参数")
@Data
public class UserQuery {
    @ApiModelProperty(value = "用户名")
    private String username;
}

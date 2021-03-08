package com.codetc.web.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("注册参数")
@Data
public class UserParam {
    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    @Length(min = 5, max = 12, message = "用户名长度为5~12个字符")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 18, message = "密码长度为6~18个字符")
    private String password;

    @ApiModelProperty(value = "角色ID", required = true, example = "1")
    @NotNull(message = "角色不能为空")
    @Min(1)
    private Integer roleId;
}

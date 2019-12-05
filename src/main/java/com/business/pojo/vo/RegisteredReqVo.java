package com.business.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 注册实
 * RegisteredReqVo
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-9-5 17:46
 */
@Data
public class RegisteredReqVo {

    @NotEmpty
    @ApiModelProperty(name = "userId", value = "用户名", required = true)
    private String username;

    @NotEmpty
    @ApiModelProperty(name = "password", value = "用户密码", required = true)
    private String password;

    @NotEmpty
    @ApiModelProperty(name = "role", value = "权限")
    private String role;
}

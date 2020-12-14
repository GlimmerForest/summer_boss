package com.william.boss.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author john
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@ApiModel("用户管理模块")
public class UserQueryParams {

    @ApiModelProperty(value = "用户名", position = 1)
    private String username;

    @ApiModelProperty(value = "拥有的租户", position = 2)
    private List<Integer> tenantIds;

    @ApiModelProperty(value = "用户类型 1: 系统用户 2: 接口用户", position = 3)
    private String userType;

    @ApiModelProperty(value = "状态 0: 禁用 1: 启用", position = 4)
    private String status;

    @ApiModelProperty(value = "手机号", position = 5)
    private String mobile;
}

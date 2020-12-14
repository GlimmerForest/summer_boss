package com.william.boss.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
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
public class UserVO {

    @ApiModelProperty(value = "用户id", position = 1)
    private Integer id;

    @ApiModelProperty(value = "用户名", position = 2)
    private String username;

    @ApiModelProperty(value = "密码", position = 3)
    private String password;

    @ApiModelProperty(value = "上次登录时间", position = 4)
    private String lastLoginTime;

    @ApiModelProperty(value = "登录错误次数", position = 5)
    private Integer loginErrorCount;

    @ApiModelProperty(value = "拥有的租户", position = 6)
    private List<String> tenants;

    @ApiModelProperty(value = "拥有的租户", position = 7)
    private List<Integer> tenantIds;

    @ApiModelProperty(value = "用户类型 1: 系统用户 2: 接口用户", position = 8)
    private String userType;

    @ApiModelProperty(value = "状态 0: 禁用 1: 启用", position = 9)
    private String status;

    @ApiModelProperty(value = "用户头像", position = 10)
    private String profilePhoto;

    @ApiModelProperty(value = "注册时间", position = 11)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "用户昵称", position = 12)
    private String nickname;

    @ApiModelProperty(value = "生日", position = 13)
    private LocalDateTime birthday;

    @ApiModelProperty(value = "地址", position = 14)
    private String address;

    @ApiModelProperty(value = "手机号", position = 15)
    private String mobile;

    @ApiModelProperty(value = "邮箱", position = 16)
    private String email;

    @ApiModelProperty(value = "性别", position = 17)
    private String gender;
}

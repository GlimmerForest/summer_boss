package com.william.boss.orm.model;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author john
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class UserInfo {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 用户性别
     */
    private String gender;
}

package com.william.boss.service;

import com.william.boss.orm.model.UserInfo;

import java.util.List;

/**
 * @author john
 */
public interface IUserService {
    /**
     * 查询用户列表
     * @return 用户列表
     */
    List<UserInfo> getAllUsers();

    /**
     * 添加用户信息
     * @param userInfo 用户信息
     */
    void addOrEdit(UserInfo userInfo);

    /**
     * 查询用户信息
     * @param id 用户id
     * @return 用户信息
     */
    UserInfo detail(Integer id);

    /**
     * 删除用户
     * @param id 用户id
     */
    void delete(Integer id);
}

package com.william.boss.service;

import com.william.boss.vo.user.UserQueryParams;
import com.william.boss.vo.user.UserVO;

import java.util.List;

/**
 * @author john
 */
public interface IPlatformUserService {

    /**
     * 添加用户
     * @param userVO 用户信息
     */
    void save(UserVO userVO);

    /**
     * 更新用户信息
     * @param userVO 用户信息
     */
    void modify(UserVO userVO);

    /**
     * 获取用户信息
     * @param id 用户id
     * @return 用户信息
     */
    UserVO get(Integer id);

    /**
     * 用户列表
     * @param params 查询参数
     * @return 用户列表
     */
    List<UserVO> getUsers(UserQueryParams params);

    /**
     * 查询用户数量
     * @param params 查询参数
     * @return 用户数量
     */
    Integer getUsersCount(UserQueryParams params);

}

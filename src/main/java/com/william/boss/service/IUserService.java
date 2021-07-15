package com.william.boss.service;


import com.william.boss.dto.UserDTO;

import java.util.List;

/**
 * @author john
 */
public interface IUserService {
    /**
     * 查询用户列表
     * @return 用户列表
     */
    List<UserDTO> getAllUsers();

    /**
     * 添加用户信息
     * @param userDTO 用户信息
     */
    void addOrEdit(UserDTO userDTO);

    /**
     * 查询用户信息
     * @param id 用户id
     * @return 用户信息
     */
    UserDTO detail(Integer id);

    /**
     * 删除用户
     * @param id 用户id
     */
    void delete(Integer id);
}

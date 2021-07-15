package com.william.boss.controller;


import com.william.boss.service.IPlatformUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 平台用户，用于登录系统 前端控制器
 * </p>
 *
 * @author john.wang
 * @since 2021-07-15
 */
@RestController
@RequestMapping("/platformUser")
public class PlatformUserController {

    @Resource
    private IPlatformUserService userService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

}


package com.william.boss.controller;

import com.william.boss.service.IPlatformUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author john
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class PlatformUserController {
    @Resource
    private IPlatformUserService userService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

}

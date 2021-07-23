package com.william.boss.controller;

import com.william.boss.service.RedisService;
import com.william.boss.utils.AesUtil;
import com.william.boss.vo.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping("/auth")
@Api(tags = "登录模块")
public class LoginController {

    @Resource
    private RedisService<String> redisService;

    @GetMapping("/token")
    @ApiOperation("第三方应用获取token接口")
    public ResponseResult<?> getAccessToken(@ApiParam(example = "testCode") @RequestParam String code) {
        String param = AesUtil.decrypt(code);

        Assert.hasText(param, "");

        return ResponseResult.SUCCESS;
    }

    @GetMapping("/code")
    public ResponseResult<?> getCode(@ApiParam(example = "admin") @RequestParam String appId,
                                     @ApiParam(example = "123456") @RequestParam String appSecret) {

        return ResponseResult.SUCCESS;
    }

    @GetMapping("/login")
    public ResponseResult<?> login(@ApiParam(example = "admin") @RequestParam String username,
                                   @ApiParam(example = "123456") @RequestParam String passwd) {

        return ResponseResult.SUCCESS;
    }

    public static class CodeInfo {

    }

    public static class TokenInfo {}
}

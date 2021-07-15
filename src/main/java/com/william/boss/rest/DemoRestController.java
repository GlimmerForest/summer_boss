package com.william.boss.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author john
 */
@Slf4j
@RestController
@RequestMapping("/test")
@Api(tags = "demo")
public class DemoRestController {

    @Resource
    private DataSource dataSource;

    @GetMapping("/1")
    @ApiOperation("echo")
    public String echo(@RequestParam String content) {
        log.info(dataSource.toString());
        return content;
    }
}

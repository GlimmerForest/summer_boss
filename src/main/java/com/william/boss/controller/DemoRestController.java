package com.william.boss.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author john
 */
@Slf4j
@RestController
@RequestMapping("/rest")
public class DemoRestController {

    @Resource
    private DataSource dataSource;

    @RequestMapping("/1")
    public String echo(String content) {
        log.info(dataSource.toString());
        return content;
    }
}

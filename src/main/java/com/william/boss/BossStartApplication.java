package com.william.boss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author john
 */
@MapperScan("com.william.boss.orm")
@SpringBootApplication(scanBasePackages = "com.william.boss")
public class BossStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(BossStartApplication.class, args);
    }
}

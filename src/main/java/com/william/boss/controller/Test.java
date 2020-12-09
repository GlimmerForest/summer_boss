package com.william.boss.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

@Getter
@Setter
public class Test {
    private String name;

    public void configFromPropety(Properties properties) {
        {
            String property = properties.getProperty("druid.name");
            if (property != null) {
                this.setName(property);
            }
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        Properties properties = new Properties();
        properties.setProperty("druid.name", "test");
        test.configFromPropety(properties);
    }
}

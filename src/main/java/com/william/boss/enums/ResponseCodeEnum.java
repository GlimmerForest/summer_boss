package com.william.boss.enums;

import lombok.Getter;

/**
 * @author john
 */
@Getter
public enum ResponseCodeEnum {

    /**
     * 系统内部异常
     */
    SYSTEM_ERROR(500, "Internal Server Error"),
    /**
     * 请求成功
     */
    SUCCESS(200, "success");

    ResponseCodeEnum(Integer  code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer  code;
    private String message;
}

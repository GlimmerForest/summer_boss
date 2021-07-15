package com.william.boss.exception;

import com.william.boss.enums.ResponseCodeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException{

    public BusinessException(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public BusinessException(ResponseCodeEnum err) {
        super();
        this.err = err;
    }

    private int code;
    private String message;

    private ResponseCodeEnum err;
}

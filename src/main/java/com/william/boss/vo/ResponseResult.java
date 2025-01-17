package com.william.boss.vo;

import com.william.boss.enums.ResponseCodeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * 返回工具类
 * @author john.wang
 */
@Setter
@Getter
@Accessors(chain = true)
@ToString
@NoArgsConstructor
public class ResponseResult<T> {
    private Integer  code;
    private String message;
    private T data;

    public static final ResponseResult<?> ERROR = new ResponseResult<>(ResponseCodeEnum.SYSTEM_ERROR);
    public static final ResponseResult<?> SUCCESS = new ResponseResult<>(ResponseCodeEnum.SUCCESS);

    public ResponseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(HttpStatus httpStatus) {
        this.code = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
    }

    public ResponseResult(HttpStatus httpStatus, T data) {
        this.code = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
        this.data = data;
    }

    public ResponseResult(ResponseCodeEnum responseCodeEnum) {
        this.code = responseCodeEnum.getCode();
        this.message = responseCodeEnum.getMessage();
    }

    public ResponseResult(ResponseCodeEnum responseCodeEnum, T data) {
        this.code = responseCodeEnum.getCode();
        this.message = responseCodeEnum.getMessage();
        this.data = data;
    }

    public static <T> ResponseResult<T> failure(HttpStatus httpStatus) {
        return new ResponseResult<>(httpStatus);
    }

    public static <T> ResponseResult<T> failure(Integer code, String message) {
        return new ResponseResult<>(code, message);
    }

    public static <T> ResponseResult<T> failure(ResponseCodeEnum responseCodeEnum) {
        return new ResponseResult<>(responseCodeEnum);
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(HttpStatus.OK, data);
    }

    public static <T> ResponseResult<SimplePage<T>> success(Long total, List<T> data) {
        SimplePage<T> simplePage = new SimplePage<>(total, data);
        return new ResponseResult<>(ResponseCodeEnum.SUCCESS, simplePage);
    }

}

package com.william.boss.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.william.boss.enums.ResponseCodeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author john
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
    @JsonIgnore
    private boolean isJson=false;

    public static final ResponseResult<?> ERROR = new ResponseResult<>(ResponseCodeEnum.SYSTEM_ERROR);
    public static final ResponseResult<?> SUCCESS = new ResponseResult<>(ResponseCodeEnum.SUCCESS);

    public ResponseResult(HttpStatus httpStatus) {
        this.code=httpStatus.value();
        this.message=httpStatus.getReasonPhrase();
    }

    public ResponseResult(HttpStatus httpStatus, T data){
        this.code=httpStatus.value();
        this.message=httpStatus.getReasonPhrase();
        this.data = data;
    }

    public ResponseResult(ResponseCodeEnum responseCodeEnum){
        this.code = responseCodeEnum.getCode();
        this.message = responseCodeEnum.getMessage();
    }

    public ResponseResult(ResponseCodeEnum responseCodeEnum, T data){
        this.code = responseCodeEnum.getCode();
        this.message = responseCodeEnum.getMessage();
        this.data = data;
    }

    public static ResponseResult<?> failure(HttpStatus httpStatus){
        return new ResponseResult<>(httpStatus);
    }

    public static ResponseResult<?> success(HttpStatus httpStatus){
        return new ResponseResult<>(httpStatus);
    }

    public ResponseResult<T> success(HttpStatus httpStatus, T result){
    	return new ResponseResult<>(httpStatus, result);
    }

    public ResponseResult<?> success(Number total, Object data){
        Map<String,Object> map = new HashMap<>(2);
        map.put("total",total);
        map.put("data",data);
        return new ResponseResult<>(ResponseCodeEnum.SUCCESS, map);
    }

}

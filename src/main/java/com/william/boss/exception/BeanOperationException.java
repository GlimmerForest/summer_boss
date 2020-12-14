package com.william.boss.exception;

/**
 * @author john
 */
public class BeanOperationException extends RuntimeException{

    public BeanOperationException(String msg) {
        super(msg);
    }

    public BeanOperationException(String msg, Throwable ex) {
        super(msg, ex);
    }
}

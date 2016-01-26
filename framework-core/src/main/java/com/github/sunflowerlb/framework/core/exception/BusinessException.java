package com.github.sunflowerlb.framework.core.exception;

/**
 * 业务异常的父类
 * 
 * @author platform
 *
 */
public class BusinessException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public BusinessException(int code, String message) {
        super(code, message);
    }

    public BusinessException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(int code, String message, Object[] args) {
        super(code, message, args);
    }

    public BusinessException(int code, String message, Throwable cause, Object[] args) {
        super(code, message, cause, args);
    }

    public BusinessException(String message, Object[] args) {
        super(message, args);
    }

    public BusinessException(String message, Throwable cause, Object[] args) {
        super(message, cause, args);
    }

}

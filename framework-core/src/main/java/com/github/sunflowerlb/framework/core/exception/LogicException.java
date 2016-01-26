package com.github.sunflowerlb.framework.core.exception;

/**
 * 逻辑异常的父类
 * 
 * @author platform
 *
 */
public class LogicException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public LogicException(int code, String message) {
        super(code, message);
    }

    public LogicException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public LogicException(String message) {
        super(message);
    }

    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogicException(int code, String message, Object[] args) {
        super(code, message, args);
    }

    public LogicException(int code, String message, Throwable cause, Object[] args) {
        super(code, message, cause, args);
    }

    public LogicException(String message, Object[] args) {
        super(message, args);
    }

    public LogicException(String message, Throwable cause, Object[] args) {
        super(message, cause, args);
    }
    
}

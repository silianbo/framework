package com.github.sunflowerlb.framework.core.exception;

/**
 * 平台异常的父类
 * 
 * @author platform
 *
 */
public class PlatformException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public PlatformException(int code, String message) {
        super(code, message);
    }

    public PlatformException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public PlatformException(String message) {
        super(message);
    }

    public PlatformException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlatformException(int code, String message, Object[] args) {
        super(code, message, args);
    }

    public PlatformException(int code, String message, Throwable cause, Object[] args) {
        super(code, message, cause, args);
    }

    public PlatformException(String message, Object[] args) {
        super(message, args);
    }

    public PlatformException(String message, Throwable cause, Object[] args) {
        super(message, cause, args);
    }

    
}

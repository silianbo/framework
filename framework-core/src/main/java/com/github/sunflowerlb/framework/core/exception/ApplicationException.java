package com.github.sunflowerlb.framework.core.exception;

/**
 * 异常基类, 带异常码
 * 
 * @author platform
 * 
 */
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 异常错误码
     */
    protected int code;

    /**
     * 异常信息的参数
     */
    protected Object[] args;

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ApplicationException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ApplicationException(String message, Object[] args) {
        super(message);
        this.args = args;
    }

    public ApplicationException(String message, Throwable cause, Object[] args) {
        super(message, cause);
        this.args = args;
    }

    public ApplicationException(int code, String message, Object[] args) {
        super(message);
        this.code = code;
        this.args = args;
    }

    public ApplicationException(int code, String message, Throwable cause, Object[] args) {
        super(message, cause);
        this.code = code;
        this.args = args;
    }

    public int getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

}

package com.github.sunflowerlb.framework.tools.util;

/**
 * 日期格式化异常
 * 
 * @author 464281
 */
public class DateParseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DateParseException() {
        super();
    }

    public DateParseException(String message) {
        super(message);
    }

    public DateParseException(Throwable t) {
        super(t);
    }

    public DateParseException(String message, Throwable t) {
        super(message, t);
    }

}

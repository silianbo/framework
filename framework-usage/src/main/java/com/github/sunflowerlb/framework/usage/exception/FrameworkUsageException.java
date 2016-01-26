/**
 * 
 */
package com.github.sunflowerlb.framework.usage.exception;

import com.github.sunflowerlb.framework.core.exception.PlatformException;

/**
 * 
 * @author lb
 */
public class FrameworkUsageException extends PlatformException {

    private static final long serialVersionUID = 2869830106797789492L;

    public FrameworkUsageException(int code, String message, Object[] args) {
        super(code, message, args);
    }

    public FrameworkUsageException(int code, String message, Throwable cause, Object[] args) {
        super(code, message, cause, args);
    }

    public FrameworkUsageException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public FrameworkUsageException(int code, String message) {
        super(code, message);
    }

    public FrameworkUsageException(String message, Object[] args) {
        super(message, args);
    }

    public FrameworkUsageException(String message, Throwable cause, Object[] args) {
        super(message, cause, args);
    }

    public FrameworkUsageException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrameworkUsageException(String message) {
        super(message);
    }

}

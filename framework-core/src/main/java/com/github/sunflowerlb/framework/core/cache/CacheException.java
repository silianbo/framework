package com.github.sunflowerlb.framework.core.cache;

/**
 * 缓存异常
 * 
 * @author lb
 */
public class CacheException extends RuntimeException {

	/** serialVersionUID */
	private static final long serialVersionUID = 4543596505445947947L;

	public CacheException() {
		super();
	}

	public CacheException(String message) {
		super(message);
	}

	public CacheException(String message, Throwable cause) {
		super(message, cause);
	}

	public CacheException(Throwable cause) {
		super(cause);
	}
}

package com.github.sunflowerlb.framework.web.session;

/**
 * 分布式会话异常
 * 
 * @author 464281
 * 
 */
public class DistributedSessionException extends RuntimeException {

	/** serialVersionUID */
	private static final long serialVersionUID = 7598812591172068549L;

	public DistributedSessionException() {
		super();
	}

	public DistributedSessionException(String message) {
		super(message);
	}

	public DistributedSessionException(String message, Throwable cause) {
		super(message, cause);
	}

	public DistributedSessionException(Throwable cause) {
		super(cause);
	}
}

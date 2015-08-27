/**
 * 
 */
package com.lb.framework.usage.exception;

import com.lb.framework.core.commons.IErrors;
import com.lb.framework.core.exception.ApplicationException;

/**
 * 
 * @author lb
 */
public enum ErrOnly implements IErrors<ErrOnly> {

	SUCCESS(200, "操作成功"),
	
	USER_NAME_INVALID(3001, "用户名[%s]异常"),

	USER_NAME_EMPTY(3002, "用户名不允许为空"),

	LOGIN_FAIL(3003, "登陆失败"),

	TEST_ERROR(3004, "参数[%s], [%s]异常"),

	UNKNOWN(3999, "操作失败(请联系管理员)");

	private int code;

	private String message;

	private ErrOnly(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public ErrOnly parse() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ihome.framework.core.commons.IErrors#parse(java.lang.Object[])
	 */
	@Override
	public ErrOnly parse(Object... args) {
		this.message = String.format(message, args);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ihome.framework.core.commons.IErrors#parseMsg(java.lang.String,
	 * java.lang.Object[])
	 */
	@Override
	public ErrOnly parseMsg(String message, Object... args) {
		this.message = message;
		return parse(args);
	}

	/* (non-Javadoc)
	 * @see com.ihome.framework.core.commons.IErrors#exp()
	 */
	@Override
	public ApplicationException exp() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ihome.framework.core.commons.IErrors#exp(java.lang.Object[])
	 */
	@Override
	public ApplicationException exp(Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ihome.framework.core.commons.IErrors#expMsg(java.lang.String, java.lang.Object[])
	 */
	@Override
	public ApplicationException expMsg(String message, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationException exp(Throwable cause, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationException expMsg(String message, Throwable cause, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}
}

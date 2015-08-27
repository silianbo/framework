package com.lb.framework.usage.exception;

import com.lb.framework.core.commons.IErrors;
import com.lb.framework.core.commons.OpResponse;
import com.lb.framework.core.exception.ApplicationException;

/**
 * 
 * 错误码和错误消息定义
 * 
 * @author lb
 */
public enum ErrCode implements IErrors<OpResponse> {

	USER_NAME_INVALID(3001, "用户名[%s]异常"),

	USER_NAME_EMPTY(3002, "用户名不允许为空"),

	LOGIN_FAIL(3003, "登陆失败"),

	TEST_ERROR(3004, "参数[%s], [%s]异常"),

	FILE_UPLOAD_FAIL(3005, "图片上传出错"),
	
	CODE_NOT_EXIST_IN_FILE(3998, "不存在配置文件中的code"),
	
	UNKNOWN(3999, "操作失败(请联系管理员)");

	private int code;

	private String message;

	private ErrCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public OpResponse parse() {
		OpResponse result = new OpResponse();
		result.setCode(code);
		result.setMessage(message);
		return result;
	}

	public OpResponse parse(Object... args) {
		OpResponse result = new OpResponse();
		result.setCode(code);
		String formatReason = String.format(message, args);
		result.setMessage(formatReason);
		return result;
	}
	
	@Override
	public OpResponse parseMsg(String message, Object... args) {
		this.message = message;
		return parse(args);
	}

	public FrameworkUsageException exp() {
		return new FrameworkUsageException(code, message);
	}
	
	public FrameworkUsageException exp(Object... args) {
		String formatReason = String.format(message, args);
		return new FrameworkUsageException(code, formatReason, args);
	}

	public FrameworkUsageException expMsg(String message, Object... args) {
		String formatReason = String.format(message, args);
		return new FrameworkUsageException(code, formatReason, args);
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
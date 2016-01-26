package com.github.sunflowerlb.framework.web.servlet.json;

/**
 * Json处理返回信息对象
 * <p>
 * 提供给业务系统使用，同时框架也能感知到业务的处理结果，比如成功或者失败，从而能对json返回进行一定的处理，比如异常处理
 * 
 * @author 464281
 */
public class JsonMessage {

	public static final String SUCCESS_CODE = "200";

	public static final String SUCCESS_MESSAGE = "操作成功";

	public static final String SYSTEM_ERROR_CODE = "500";

	public static final String SYSTEM_ERROR_MESSAGE = "系统错误";

	/** 结果码，不能为<code>NULL</code> */
	private String code = SYSTEM_ERROR_CODE;

	/** 结果信息，当处理失败时，不能为<code>NULL</code> */
	private String message = SYSTEM_ERROR_MESSAGE;

	/** 结果对象，可以为<code>NULL</code> */
	private Object data;
	
	/** 表单防重复的token */
	private String token;

	public JsonMessage() {

	}

	public JsonMessage(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public static JsonMessage suc() {
		JsonMessage jsonMsg = new JsonMessage(SUCCESS_CODE, SUCCESS_MESSAGE);
		return jsonMsg;
	}
	
	public static JsonMessage fail() {
		JsonMessage jsonMsg = new JsonMessage(SYSTEM_ERROR_CODE, SYSTEM_ERROR_MESSAGE);
		return jsonMsg;
	}

	public boolean isSuc() {
		return SUCCESS_CODE.equals(code);
	}

	public String getCode() {
		return code;
	}

	public JsonMessage setCode(String code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public JsonMessage setMessage(String message) {
		this.message = message;
		return this;
	}

	public Object getData() {
		return data;
	}

	public JsonMessage setData(Object data) {
		this.data = data;
		return this;
	}

	public String getToken() {
		return token;
	}

	public JsonMessage setToken(String token) {
		this.token = token;
		return this;
	}
	
}

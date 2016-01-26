package com.github.sunflowerlb.framework.core.commons;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 用来保存一些操作的结果
 * 
 * @author lb
 */
public class OpResponseBak implements Serializable {

    private static final long serialVersionUID = 2331099959379685238L;

    protected int code;

	protected String message;

	protected Map<String, Object> params = new HashMap<String, Object>();

	public static final int SUCCESS = 200;

	public static OpResponseBak suc() {
		return suc(null);
	}

	public static OpResponseBak suc(String message) {
		OpResponseBak result = new OpResponseBak();
		result.code = SUCCESS;
		result.message = message;
		return result;
	}

	public boolean isSuc() {
		return (code == SUCCESS);
	}

	public boolean isFail() {
		return !isSuc();
	}

	public void addParam(String key, Object value) {
		params.put(key, value);
	}

	public Object getParam(String key) {
		return params.get(key);
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "OpResponseBak [code=" + code + ", message=" + message + ", params=" + params + "]";
	}
}

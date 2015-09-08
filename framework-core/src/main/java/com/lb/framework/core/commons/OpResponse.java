package com.lb.framework.core.commons;

import java.io.Serializable;

/**
 * 用来保存一些操作的结果
 * 
 * @author lb
 */
public class OpResponse implements Serializable {

    private static final long serialVersionUID = 2331099959379685238L;

    /**
     * 应用返回的错误码
     */
    protected int code;

    /**
     * 应用返回的(错误)信息
     */
	protected String message;

	/**
	 * 应用返回的数据
	 */
	protected Object data;

	/**
	 * 某次请求返回的token，用于下一次表单提交
	 */
	protected String token;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "OpResponse [code=" + code + ", message=" + message + ", data=" + data + ", token=" + token + "]";
    }
    
}

package com.github.sunflowerlb.framework.core.commons;

import java.io.Serializable;

/**
 * 用来保存一些操作的结果,提供给业务系统使用；
 * 可以用在web和app之间的交互，也可以用于通常的方法调用等；
 * @author lb
 */
public class OpResponse implements Serializable {

    private static final long serialVersionUID = 2331099959379685238L;

    /**
     * 成功的code，最好是各个业务系统都保持统一
     */
    public static final int SUCCESS_CODE = 1;

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

    /**
     * 返回1个表示成功的实例
     *
     * @return
     */
    public static OpResponse suc() {
        OpResponse resp = new OpResponse();
        resp.code = SUCCESS_CODE;
        return resp;
    }

    /**
     * 返回1个表示成功的实例
     *
     * @param message
     *            要返回的消息
     * @return
     */
    public static OpResponse suc(String message) {
        OpResponse resp = OpResponse.suc();
        resp.message = message;
        return resp;
    }

    /**
     * 返回1个表示失败的实例
     * @param code 错误码
     * @param message 错误信息
     * @return
     */
    public static OpResponse fail(int code, String message) {
        OpResponse resp = new OpResponse();
        resp.code = code;
        resp.message = message;
        return resp;
    }

    /**
     * 返回1个表示失败的实例 
     * @param code 错误码
     * @param message 错误信息
     * @return
     */
    public static OpResponse fail(int code, String message, String next) {
        OpResponse resp = new OpResponse();
        resp.code = code;
        resp.message = message;
        return resp;
    }

    /**
     * 操作结果是否成功
     *
     * @return
     */
    public boolean isSuc() {
        return code == SUCCESS_CODE;
    }

    /**
     * 操作结果是否失败
     * @return
     */
    public boolean isFail() {
        return !isSuc();
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

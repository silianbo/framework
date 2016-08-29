package com.github.sunflowerlb.framework.core;


import java.io.Serializable;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.collect.Maps;

/**
 * 通用结果封装类
 *
 * @param <T>
 * @author Administrator
 */
public class Result<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3325906776958676536L;

    /**
     * 结果是否正确
     */
    private boolean isOK;

    /**
     * 错误代码，一般不是非常关心错误代码，可以忽略该字段，为空在json序列化时不输出
     */
    @JSONField(serialzeFeatures = {SerializerFeature.WriteMapNullValue})
    //@JsonInclude(Include.NON_NULL)
    private String errCode;

    /**
     * 错误信息，也可以直接作为返回信息使用
     */
    private String errMsg;

    /**
     * 结果数据，需要携带结果数据的，可以设置该字段，为空在json序列化时不输出
     */
    @JSONField(serialzeFeatures = {SerializerFeature.WriteMapNullValue})
    //@JsonInclude(Include.NON_NULL)
    private T data;

    /**
     * 结果辅助属性，结果主数据的辅助数据，为空在json序列化时不输出
     */
    @JSONField(serialzeFeatures = {SerializerFeature.WriteMapNullValue})
    //@JsonInclude(Include.NON_NULL)
    private Map<String, Object> attrs;

    @JSONField(serialzeFeatures = {SerializerFeature.WriteMapNullValue})
    //@JsonInclude(Include.NON_NULL)
    private String[] errors;

    public Result() {

    }

    public Result(boolean isOk, String errMsg) {
        this.isOK = isOk;
        this.errMsg = errMsg;
    }

    public Result(Boolean isOk, String errMsg, T data) {
        this(isOk, errMsg);
        this.data = data;
    }

    public Result(Boolean isOk, String errMsg, T data, Map<String, Object> attrs) {
        this(isOk, errMsg);
        this.data = data;
        this.attrs = attrs;
    }

    public String getErrCode() {
        return errCode;
    }

    public Result<T> setErrCode(String errCode) {
        this.errCode = errCode;
        return this;
    }

    public boolean getIsOK() {
        return isOK;
    }

    public Result<T> setIsOK(boolean isOk) {
        this.isOK = isOk;
        return this;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public Result<T> setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Map<String, Object> getAttrs() {
        return attrs;
    }

    public Result<T> setAttrs(Map<String, Object> attrs) {
        this.attrs = attrs;
        return this;
    }

    public Result<T> addAttr(String key, Object value) {
        if (this.attrs == null) {
            this.attrs = Maps.newHashMap();
        }
        this.attrs.put(key, value);
        return this;
    }

    /**
     * 获取辅助属性，指定辅助属性的返回类型，如果返回类型不匹配将抛出ClassCastException
     *
     * @param key
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
    public <R> R getAttr(String key, Class<R> type) {
        if (attrs == null || attrs.isEmpty()) {
            return null;
        }
        return (R) this.attrs.get(key);
    }

    /**
     * 获取辅助属性，如果发生类型转换异常，静默返回
     *
     * @param key
     * @param type
     * @return
     */
    public <R> R getAttrQuiet(String key, Class<R> type) {
        try {
            return getAttr(key, type);
        } catch (ClassCastException e) {
        }
        return null;
    }

    public String[] getErrors() {
        return errors;
    }

    public void setErrors(String[] errors) {
        this.errors = errors;
    }

    /**
     * 创建失败结果对象
     *
     * @param errMsg
     * @return
     */
    public static Result<Void> fail(String errMsg) {
        return new Result<Void>(Boolean.FALSE, errMsg);
    }

    /**
     * 创建失败结果对象，包含数据信息
     *
     * @param errMsg
     * @param data
     * @return
     */
    public static <D> Result<D> fail(String errMsg, D data) {
        return new Result<D>(Boolean.FALSE, errMsg, data);
    }

    /**
     * 创建失败结果对象，包含数据信息和辅助属性信息
     *
     * @param errMsg
     * @param data
     * @param attrs
     * @return
     */
    public static <D> Result<D> fail(String errMsg, D data, Map<String, Object> attrs) {
        return new Result<D>(Boolean.FALSE, errMsg, data, attrs);
    }

    /**
     * 创建失败对象，从另外一个失败对象复制异常消息
     *
     * @param result
     * @return
     */
    public static Result<Void> fail(Result<?> result) {
        return fail(result != null ? result.getErrMsg() : "");
    }

    /**
     * 创建成功结果对象
     *
     * @param errMsg
     * @return
     */
    public static Result<Void> success(String errMsg) {
        return new Result<Void>(Boolean.TRUE, errMsg);
    }

    /**
     * 创建成功结果对象，包含数据信息
     *
     * @param errMsg
     * @param data
     * @return
     */
    public static <D> Result<D> success(String errMsg, D data) {
        return new Result<D>(Boolean.TRUE, errMsg, data);
    }

    /**
     * 创建成功结果对象，包含数据信息
     *
     * @param data
     * @return
     */
    public static <D> Result<D> success(D data) {
        return new Result<D>(Boolean.TRUE, "", data);
    }
}
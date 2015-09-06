package com.lb.framework.core.log;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志规范的工具封装
 * 
 * @author lb
 */
public class Log {

	/**
	 * 操作类型
	 */
	private String operation;

	/**
	 * 日志信息
	 */
	private String message;

	/**
	 * 额外的参数
	 */
	private Map<String, Object> params = new HashMap<String, Object>();

	/**
	 * 日志格式 [operation][message],{key1=value1,key2=value2,key3=value3}
	 */
	private static final String LOG_FORMAT = "%d|%s|%s|%s";

	public Log(String operation) {
		this.operation = operation;
	}

	public static Log op(String op) {
		return new Log(op);
	}

	public Log msg(String message) {
		this.message = message;
		return this;
	}

	public Log kv(String key, Object value) {
		params.put(key, value);
		return this;
	}

	public Log kvs(Map<String, Object> maps) {
		params.putAll(maps);
		return this;
	}
	
	@Override
	public String toString() {
		return String.format(LOG_FORMAT, System.currentTimeMillis(), operation, params.toString(), message);
	}

	public static void main(String[] args) {
		String s = Log.op("createOrderOp").msg("create order success!").kv("name", "zhengxiahong").kv("id", 12345).toString();
		System.out.println(s);
	}

}

package com.github.sunflowerlb.framework.core.cache;

/**
 * 缓存客户端的接口，定义了几个基本的操作
 * @author lb
 */
public interface IHomeCacheClient {

	/**
	 * 放入缓存
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            值
	 */
	void set(String key, Object value);

	/**
	 * 放入缓存
	 * 
	 * @param key
	 *            key
	 * @param exp
	 *            过去时间，单位秒
	 * @param value
	 *            值
	 */
	void set(String key, Object value, int exp);

	/**
	 * 从缓存中获取对象
	 * 
	 * @param key
	 *            key
	 * @return Object
	 */
	Object get(String key);

	/**
	 * 根据key获取对象
	 * 
	 * @param key
	 *            key
	 * @param t
	 *            类型
	 * @return object
	 */
	<T> T get(String key, Class<T> t);

	/**
	 * 从缓存中删除
	 * 
	 * @param key
	 *            cache key
	 */
	void delete(String key);
}

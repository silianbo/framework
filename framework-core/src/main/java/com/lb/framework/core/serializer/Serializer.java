package com.lb.framework.core.serializer;

/**
 * 序列化组件
 * <p>
 * 提供序列化和反序列化功能
 * 
 * @author lb
 */
public interface Serializer<T> {

	/**
	 * 序列化
	 * 
	 * @param t
	 *            待序列化对象
	 * @return 序列化后的byte数组
	 * @throws SerializeException
	 *             当序列化流出现异常时抛出
	 */
	byte[] serialize(T t);

	/**
	 * 反序列化
	 * 
	 * @param bytes
	 *            待反序列化的byte数组
	 * @return 反序列化后的对象
	 * @throws SerializeException
	 *             当反序列化流出现异常时抛出
	 */
	T deserialize(byte[] bytes);
}

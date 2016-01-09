package com.lb.framework.core.serializer;

import java.io.IOException;

/**
 * 抽象的序列化组件
 * 
 * @author lb
 * @see com.ihome.framework.core.serializer.Serializer
 */
public abstract class AbstractSerializer<T> implements Serializer<T> {

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.ihome.framework.core.serializer.Serializer#serialize(java.lang.Object)
	 */
	@Override
	public byte[] serialize(T t) {
		if (null == t) {
			return null;
		}

		try {
			return doSerialize(t);
		} catch (IOException e) {
			throw new SerializeException("serialize fail!", e);
		}
	}

	protected abstract byte[] doSerialize(T t) throws IOException;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.ihome.framework.core.serializer#deserialize(byte[])
	 */
	@Override
	public T deserialize(byte[] bytes) {
		if (null == bytes) {
			return null;
		}

		try {
			return doDeserialize(bytes);
		} catch (IOException e) {
			throw new SerializeException("deserialize fail!", e);
		}
	}

	protected abstract T doDeserialize(byte[] bytes) throws IOException;
}

package com.github.sunflowerlb.framework.core.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;

/**
 * hessian序列化组件
 * 
 * @author lb
 * @see com.caucho.hessian.io.SerializerFactory
 * @see com.caucho.hessian.io.Hessian2Output
 * @see com.caucho.hessian.io.Hessian2Input
 */
public class HessianSerializer<T> extends AbstractSerializer<T> {

	/**
	 * hessian序列化工厂
	 * <p>
	 * 直接初始化，防止重复创建序列化工厂导致性能问题
	 */
	private SerializerFactory serializerFactory = new SerializerFactory();

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.ihome.framework.core.serializer.AbstractSerializer#doSerialize(java.lang.Object)
	 */
	@Override
	protected byte[] doSerialize(T t) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Hessian2Output h2out = new Hessian2Output(baos);
		h2out.setSerializerFactory(serializerFactory);
		h2out.writeObject(t);
		h2out.flush();
		return baos.toByteArray();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.ihome.framework.core.serializer.AbstractSerializer#doDeserialize(byte[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected T doDeserialize(byte[] bytes) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		Hessian2Input h2Input = new Hessian2Input(bais);
		h2Input.setSerializerFactory(serializerFactory);
		return (T) h2Input.readObject();
	}

	public void setSerializerFactory(SerializerFactory serializerFactory) {
		if (null != serializerFactory) {
			this.serializerFactory = serializerFactory;
		}
	}
}

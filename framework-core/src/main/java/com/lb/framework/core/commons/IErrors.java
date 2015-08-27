package com.lb.framework.core.commons;

import com.lb.framework.core.exception.ApplicationException;

/**
 * 错误枚举的接口类
 * @author lb
 */
public interface IErrors<T> {

	/**
	 * 采用枚举中定义的message作为返回信息
	 * @return
	 */
	public T parse();

	/**
	 * 采用枚举中定义的message作为返回信息，并传递一些参数
	 * @param args
	 * @return
	 */
	public T parse(Object... args);
	
	/**
	 * 采用枚举中定义的code，使用自定义的message作为返回信息，并可能会带上一些参数
	 * @param message
	 * @param args
	 * @return
	 */
	public T parseMsg(String message, Object...args);
	
	/**
	 * 采用枚举中定义的message作为异常的信息
	 * @return
	 */
	public ApplicationException exp();
	
	/**
	 * 采用枚举中定义的message作为异常信息，并传递一些参数
	 * @param args
	 * @return
	 */
	public ApplicationException exp(Object... args);
	
   /**
     * 采用枚举中定义的message作为异常信息，并传递一些参数，支持传入底层的异常
     * @param cause
     * @param args
     * @return
     */
    public ApplicationException exp(Throwable cause, Object... args);

	/**
	 * 采用枚举中定义的code，使用自定义的message作为异常信息，并可能会带上一些参数
	 * @param message
	 * @param args
	 * @return
	 */
	public ApplicationException expMsg(String message, Object... args);
	
	/**
     * 采用枚举中定义的code，使用自定义的message作为异常信息，并可能会带上一些参数，并自持传入底层的异常
     * @param message
     * @param cause
     * @param args
     * @return
     */
    public ApplicationException expMsg(String message, Throwable cause, Object... args);
}

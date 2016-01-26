/**
 * 
 */
package com.github.sunflowerlb.framework.usage.service.inner;

import org.springframework.stereotype.Service;

import com.github.sunflowerlb.framework.usage.exception.ErrCode;

/**
 * 
 * @author lb
 */
@Service
public class TestExceptionService {
	
	/**
	 * 假设这个service方法会抛出业务的异常
	 */
	public void busException() {
		throw ErrCode.LOGIN_FAIL.exp();
	}
	
	/**
	 * 假设这个service方法会抛出非业务的异常
	 */
	public void nonBusException() {
		throw new IllegalArgumentException("param name can not be null");
	}
	
	/**
	 * 假设这个service方法会抛出业务的异常,并传递一些参数给异常信息做格式化
	 */
	public void busExceptionArgs() {
		throw ErrCode.TEST_ERROR.exp("param1", 123);
	}
	
	/**
	 * 假设这个service方法会抛出业务的异常,并使用自定义的异常信息
	 */
	public void busExceptionCustomMsg() {
		throw ErrCode.LOGIN_FAIL.expMsg("用户名[%s]包含敏感字符", "64646464");
	}
}

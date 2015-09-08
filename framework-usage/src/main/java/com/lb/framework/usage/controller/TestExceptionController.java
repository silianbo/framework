/**
 * 
 */
package com.lb.framework.usage.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lb.framework.core.commons.OpResponse;
import com.lb.framework.usage.exception.ErrCode;
import com.lb.framework.usage.exception.ErrOnly;
import com.lb.framework.usage.service.inner.TestExceptionService;

/**
 * 
 * @author lb
 */
@Controller
@RequestMapping("/exp")
public class TestExceptionController {

	@Autowired
	TestExceptionService exceptionService;

	/**
	 * 使用枚举方式返回需要展示给用户看的一些错误信息，主要适用于一些可以提前检测的异常情况
	 * @param req
	 * @return
	 */
	@RequestMapping("/errcodeUsage")
	@ResponseBody
	public OpResponse errcodeUsage(HttpServletRequest req) {
		OpResponse result = new OpResponse();
		String userName = req.getParameter("userName");

		// 不需要参数的错误码
		if (StringUtils.isBlank(userName)) {
			return ErrCode.USER_NAME_EMPTY.parse();
		}
		// 需要参数的错误码
		if (userName.length() > 32) {
			return ErrCode.USER_NAME_INVALID.parse(userName);
		}
		// 自定义消息的错误码...
		if (userName.contains("xxx")) {
			return ErrCode.LOGIN_FAIL.parseMsg("用户名[%s]包含敏感字符", userName);
		}
		return result;
	}

	/**
	 * 只使用异常枚举自身，但是枚举类直接转换成JSON的话会有些问题，JSON工具类都会对枚举类型特殊处理
	 * @param req
	 * @return
	 */
	@RequestMapping("/errcodeEnumUsage")
	@ResponseBody
	public ErrOnly errcodeEnumUsage(HttpServletRequest req) {
		String userName = req.getParameter("userName");

		// 不需要参数的错误码
		if (StringUtils.isBlank(userName)) {
			return ErrOnly.USER_NAME_EMPTY;
		}
		// 需要参数的错误码
		if (userName.length() > 32) {
			return ErrOnly.USER_NAME_INVALID.parse(userName);
		}
		// 自定义消息的错误码...
		if (userName.contains("xxx")) {
			return ErrOnly.LOGIN_FAIL.parseMsg("用户名[%s]包含敏感字符", userName);
		}
		return ErrOnly.SUCCESS;
	}

	/**
	 * 非JSON请求，抛出业务异常，由异常拦截器处理，页面上会拿到异常的code和message
	 */
	@RequestMapping("/nonjson/busException")
	@ResponseBody
	public void exception() {
		exceptionService.busException();
	}

	/**
	 * 非JSON请求，抛出非业务异常，由异常拦截器处理，页面上会拿到默认的code和message
	 */
	@RequestMapping("/nonjson/nonBusException")
	@ResponseBody
	public void customException() {
		exceptionService.nonBusException();
	}

	/**
	 * JSON请求，抛出业务异常，由异常拦截器处理，会返回JSON格式的错误信息
	 */
	@RequestMapping("/json/busException.json")
	public void jsonBus() {
		exceptionService.busException();
	}

	/**
	 *  JSON请求，抛出非业务异常，由异常拦截器处理，会返回JSON格式的错误信息
	 */
	@RequestMapping("/json/nonBusException.json")
	public void jsonNonBus() {
		exceptionService.nonBusException();
	}
	
	/**
	 * 非JSON请求，抛出业务异常，给message传递一些参数
	 */
	@RequestMapping("/nonjson/busExceptionArgs")
	public void busExceptionArgs() {
		exceptionService.busExceptionArgs();
	}
	
	/**
	 * 非JSON请求，抛出业务异常，用自定义的message和参数
	 */
	@RequestMapping("/nonjson/busExceptionCustomMsg")
	public void busExceptionCustomMsg() {
		exceptionService.busExceptionCustomMsg();
	}
	
	/**
	 * 测试code不在配置文件中的异常
	 */
	@RequestMapping("/codeNotExist")
	@ResponseBody
	public void codeNotExist() {
	    throw ErrCode.CODE_NOT_EXIST_IN_FILE.exp();
	}
	
}

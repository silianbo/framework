package com.lb.framework.web.servlet.handler;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.lb.framework.web.servlet.json.JsonMessage;
import com.lb.framework.web.servlet.json.WebJsonUtil;

/**
 * 
 * 定制的抽象异常处理器
 * 
 * <p>
 * 处理指定异常，并且区分Json请求和非Json请求的异常处理，对于Json请求的异常处理，返回统一的Json信息，同时防篡改校验成功的请求，</br>
 * 重新生成一次表单
 * 
 * @author zhengxiaohong
 */
public abstract class AbstractIHomeHandlerExceptionResolver extends AbstractHandlerExceptionResolver {

	protected static final Charset UTF8 = Charset.forName("UTF-8");

	// private ServletAntiTamperFormManager servletAntiTamperFormManager;

	/**
	 * 是否RESTful服务，是的话，默认认为所有请求都是JSON请求
	 */
	protected boolean restService;

	/**
	 * (non-Javadoc)
	 */
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		if (!support(request, response, handler, ex)) {
			return null;
		}
		recordException(ex, request);
		boolean isJsonRequest = false;
		if (restService) {
			isJsonRequest = true;
		} else {
			isJsonRequest = WebJsonUtil.isJsonRequest(request, handler);
		}
		if (isJsonRequest) {
			return resolveExceptionForJson(request, response, handler, ex);
		}
		return resolveExceptionForNonJson(request, response, handler, ex);
	}

	/**
	 * 异常处理器是否支持当前异常
	 * 
	 * @param request
	 *            HTTP请求
	 * @param response
	 *            HTTP响应
	 * @param handler
	 *            请求处理器
	 * @param ex
	 *            异常对象
	 * @return 支持则返回<code>TRUE</code>
	 */
	protected abstract boolean support(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex);

	/**
	 * 记录异常信息
	 * 
	 * @param ex
	 * @param request
	 */
	protected abstract void recordException(Exception ex, HttpServletRequest request);

	/**
	 * 解析Json请求的异常
	 * 
	 * @param request
	 *            HTTP请求
	 * @param response
	 *            HTTP响应
	 * @param handler
	 *            请求处理器
	 * @param ex
	 *            异常对象
	 * @return 视图对象
	 */
	protected abstract ModelAndView resolveExceptionForJson(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex);

	/**
	 * 处理非Json请求异常
	 * 
	 * @param request
	 *            HTTP请求
	 * @param response
	 *            HTTP响应
	 * @param handler
	 *            请求处理器
	 * @param ex
	 *            异常对象
	 * @return 视图对象
	 */
	protected abstract ModelAndView resolveExceptionForNonJson(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex);

	/**
	 * 包装Json处理返回信息对象
	 * <p>
	 * 若是防篡改校验成功的Json请求，则重新创建一次防篡改表单对象
	 * 
	 * @param jsonMessage
	 *            Json处理返回信息对象
	 * @param request
	 *            HTTP请求
	 * @param ex
	 *            异常对象
	 * @return 包装后的Json处理返回信息对象
	 */
	protected JsonMessage decorateJsonMessage(JsonMessage jsonMessage, HttpServletRequest request, Exception ex) {
		// if (!(jsonMessage instanceof JsonTokenMessage)) {
		// AntiTamperServerForm serverForm =
		// servletAntiTamperFormManager.findCheckedServerForm();
		//
		// // 不为null则表示防篡改校验成功
		// if (null != serverForm) {
		// AntiTamperForm newServerForm = null;
		//
		// try {
		// newServerForm =
		// servletAntiTamperFormManager.reCreateForm(serverForm);
		// } catch (FormAntiTamperException e) {
		// throw new RuntimeException("重新创建防篡改表单出现异常", e);
		// }
		//
		// jsonMessage = new JsonTokenMessage(jsonMessage);
		// ((JsonTokenMessage)
		// jsonMessage).setFormToken(newServerForm.getToken());
		// }
		// }
		return jsonMessage;
	}

	// public void setServletAntiTamperFormManager(ServletAntiTamperFormManager
	// servletAntiTamperFormManager) {
	// this.servletAntiTamperFormManager = servletAntiTamperFormManager;
	// }

	/**
	 * @param restService
	 *            the restService to set
	 */
	public void setRestService(boolean restService) {
		this.restService = restService;
	}

}

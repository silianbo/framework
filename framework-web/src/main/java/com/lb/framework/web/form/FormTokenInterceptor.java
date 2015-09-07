package com.lb.framework.web.form;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lb.framework.web.exception.WebErrors;

public class FormTokenInterceptor implements HandlerInterceptor {

	@Resource
	TokenManager formTokenManager;

	public static final String TOKEN = "_ihome_form_token";

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(!(handler instanceof HandlerMethod)) {
			return true;
		}
		// token的验证
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		FormToken formToken = handlerMethod.getMethod().getAnnotation(FormToken.class);
		if (formToken == null) {
			return true;
		}
		if (!formToken.checkToken()) {
			return true;
		}
		String token = request.getParameter(TOKEN);
		if (StringUtils.isBlank(token)) {
			throw WebErrors.TOKEN_EMPTY.exp();
		}
		// 检查token，方法需要同步处理
		boolean checked = formTokenManager.checkAndDelToken(token);
		if (!checked) {
			throw WebErrors.TOKEN_NOT_EXIST.exp();
		}
		logger.info("checkToken, token {} exist", token);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(!(handler instanceof HandlerMethod)) {
			return;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		FormToken formToken = handlerMethod.getMethod().getAnnotation(FormToken.class);
		if (formToken == null) {
			return;
		}
		if (!formToken.generateToken()) {
			return;
		}
		String token = formTokenManager.newToken();
		modelAndView.addObject(TOKEN, token);
		logger.info("generateToken, token {}", token);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	public void setFormTokenManager(TokenManager formTokenManager) {
		this.formTokenManager = formTokenManager;
	}

}

package com.lb.framework.web.form;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lb.framework.core.commons.OpResponse;
import com.lb.framework.web.exception.WebErrors;

@Aspect
public class FormTokenAspect {

    @Resource
    TokenManager formTokenManager;

    public static final String TOKEN = "_ihome_form_token";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    HttpServletRequest request;

    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        // 获取token
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Method method = ms.getMethod();
        FormToken formToken = method.getAnnotation(FormToken.class);
        if (formToken == null) {
            return pjp.proceed();
        }
        // 检验token
        if (formToken.checkToken()) {
            String token = request.getParameter(TOKEN);
            logger.info(request.getParameterMap().toString());
            if (StringUtils.isBlank(token)) {
                throw WebErrors.TOKEN_EMPTY.exp();
            }
            // TODO 需要考虑分布式并发的情况
            boolean checked = formTokenManager.checkAndDelToken(token);
            if (!checked) {
                throw WebErrors.TOKEN_NOT_EXIST.exp();
            }
            logger.debug("checkToken, token {} exist", token);
            // 这里不返回,是因为有可能同1个方法既需要验证,又需要生成新的token,放在下面return
        }
        // 设置token
        if(formToken.generateToken()) {
            String token = formTokenManager.newToken();
            Object retValue = pjp.proceed();
            logger.debug("generateToken, token {}", token);
            if(retValue instanceof OpResponse) {
                OpResponse op = (OpResponse)retValue;
                op.setToken(token);
            } else {
                // 如果是返回到页面，则把token放到request.attribute中
                request.setAttribute(TOKEN, token);
            }
            return retValue;
        }
        return pjp.proceed();
    }

    public void setFormTokenManager(TokenManager formTokenManager) {
        this.formTokenManager = formTokenManager;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    
}

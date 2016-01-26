package com.github.sunflowerlb.framework.web.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.sunflowerlb.framework.core.log.Log;
import com.github.sunflowerlb.framework.core.log.LogOp;
import com.github.sunflowerlb.framework.tools.util.JsonUtil;
import com.github.sunflowerlb.framework.web.filter.logic.SensitiveLogConfig;
import com.github.sunflowerlb.framework.web.filter.logic.SensitiveValueFilter;

/**
 * http response的切面
 * @author lb
 *
 */
@Aspect
public class ResponseAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * 敏感的参数名配置,默认有一些规则
     */
    @Autowired(required=false)
    private SensitiveLogConfig sensitiveLogConfig;

    /**
     * 参数值的敏感过滤器
     */
    @Autowired(required=false)
    private SensitiveValueFilter valueFilter;
    
    /**
     * 新建对象的时候，建立默认规则
     */
    public ResponseAspect() {
        this.sensitiveLogConfig = new SensitiveLogConfig();
        this.valueFilter = new SensitiveValueFilter(this.sensitiveLogConfig);
    }
    
    /**
     * 拦截请求,打印相应结果
     * @param pjp 
     * @return
     * @throws Throwable 
     */
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        // 获取token
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Method method = ms.getMethod();
        // TODO 以后要注意那些非ResponseBody的情况，譬如直接加@RESTController的情况
        ResponseBody responseBody = method.getAnnotation(ResponseBody.class);
        if (responseBody == null) {
            return pjp.proceed();
        }
        Object retValue = pjp.proceed();
        if(retValue != null) {
            doLog(retValue);
        }
        return retValue;
    }

    /**
     * 打印返回日志,不能影响到主流程
     * @param retValue
     */
    private void doLog(Object retValue) {
        try{
            String json = null;
            if(valueFilter == null) {
                json = JsonUtil.toJSONString(retValue);                
            } else {
                json = JSON.toJSONString(retValue, valueFilter);
            }
            logger.info(Log.op(LogOp.WEB_RESP).msg("ResponseBody:" + json).toString());
        }catch(Throwable ex) {
            logger.debug(Log.op(LogOp.WEB_RESP).msg("exception occur:").kv("retValue", retValue).toString(), ex);
        }        
    }

    /**
     * setter，注入的时候，顺便初始化SensitiveValueFilter 
     * @param sensitiveLogConfig 
     */
    public void setSensitiveLogConfig(SensitiveLogConfig sensitiveLogConfig) {
        this.sensitiveLogConfig = sensitiveLogConfig;
        this.valueFilter = new SensitiveValueFilter(sensitiveLogConfig);
    }

    /**
     * setter
     * @param valueFilter 
     */
    public void setValueFilter(SensitiveValueFilter valueFilter) {
        this.valueFilter = valueFilter;
    }
}

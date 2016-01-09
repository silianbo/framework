package com.lb.framework.core.validator;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.parameternameprovider.ParanamerParameterNameProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lb.framework.core.exception.BusinessException;
import com.lb.framework.core.log.Log;

/**
 * 
 * @author lb
 */
@Aspect
public class ValidatorAspect {
	
	private static Logger logger = LoggerFactory.getLogger(ValidatorAspect.class);

    private ValidatorFactory validatorFactory;
    private ExecutableValidator executableValidator;

    public ValidatorAspect() {
        if (validatorFactory == null) {
            validatorFactory = Validation.byProvider(HibernateValidator.class)
                    .configure()
                    .failFast(false)
                    .parameterNameProvider(new ParanamerParameterNameProvider())
                    .buildValidatorFactory();
        }
        executableValidator = validatorFactory.getValidator().forExecutables();
    }

    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        // 方法参数校验
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Method method = ms.getMethod();
        Object target = pjp.getTarget();
        Object[] args = pjp.getArgs();
        
        Set<ConstraintViolation<Object>> violations = executableValidator.validateParameters(target, method, args);
        if (!violations.isEmpty()) {
        	String message = getViolationMsg(violations);
        	logger.error(Log.op("validator params").msg("invalid parameter").kv("message", message).toString());
            throw new BusinessException(message);
        }

        Object retVal = pjp.proceed();
        return retVal;
    }

    private String getViolationMsg(Set<ConstraintViolation<Object>> violations) {
    	StringBuilder sb = new StringBuilder();
    	for (Iterator<ConstraintViolation<Object>> iterator = violations.iterator(); iterator.hasNext();) {
			ConstraintViolation<Object> constraintViolation = iterator.next();
			String[] filedNames = constraintViolation.getPropertyPath().toString().split("\\.");
			sb.append(filedNames[filedNames.length - 1]).append("[")
				.append(constraintViolation.getInvalidValue()).append("]")
					.append(constraintViolation.getMessage()).append(",");
		}
    	return StringUtils.removeEnd(sb.toString(), ",");
    }
}

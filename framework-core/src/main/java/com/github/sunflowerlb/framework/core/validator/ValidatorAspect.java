package com.github.sunflowerlb.framework.core.validator;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

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

import com.github.sunflowerlb.framework.core.exception.BusinessException;
import com.github.sunflowerlb.framework.core.log.Log;
import com.github.sunflowerlb.framework.core.utils.MessageUtils;

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
			
			/*for (Map.Entry<String, Object> entry : constraintViolation.getConstraintDescriptor().getAttributes().entrySet()) {
				String attributeName = entry.getKey();
				Object attributeValue = entry.getValue();
				System.err.println(attributeName + "---" + attributeValue);
			}
			System.err.println("errorcode:" + constraintViolation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());*/
			
			String[] filedNames = constraintViolation.getPropertyPath().toString().split("\\.");
			//String filedName = Pattern.compile("^arg\\d+$").matcher(filedNames[0]).matches() ? filedNames[1] :filedNames[0]; 
			sb.append(filedNames[filedNames.length - 1]).append("[")
				.append(constraintViolation.getInvalidValue()).append("]")
					.append(resovleMessage(constraintViolation.getMessage())).append(",");
		}
    	return StringUtils.removeEnd(sb.toString(), ",");
    }
    
    private String resovleMessage(String message) {
    	if(StringUtils.startsWith(message, "{")) {
    		return MessageUtils.message(StringUtils.removeEnd(StringUtils.removeStart(message, "{"), "}"));
    	} else {
    		return message;
    	}
    }
    
    public static void main(String[] args) {
    	System.err.println(Pattern.compile("^arg\\d+$").matcher("arg110").matches());
	}
}

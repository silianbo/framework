package com.github.sunflowerlb.framework.web.form;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防表单重复提交的token
 * @author lb
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FormToken {
    
    // 生成token
    boolean generateToken() default false;
    
    // 检验token
    boolean checkToken() default false;
}

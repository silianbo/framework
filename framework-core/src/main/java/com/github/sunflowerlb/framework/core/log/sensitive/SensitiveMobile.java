package com.github.sunflowerlb.framework.core.log.sensitive;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.sunflowerlb.framework.core.log.CryptoConvertConfig.DefaultPhoneCryptoConvertor;
import com.github.sunflowerlb.framework.core.log.ICryptoConvertor;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD})
public @interface SensitiveMobile{
	public Class<? extends ICryptoConvertor> convertor() default DefaultPhoneCryptoConvertor.class; 
}

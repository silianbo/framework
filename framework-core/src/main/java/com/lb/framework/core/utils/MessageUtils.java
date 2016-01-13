package com.lb.framework.core.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 
 * <p>Date: 13-2-9 下午8:49
 * <p>Version: 1.0
 */
public class MessageUtils {

	private static MessageSource messageSource;

    /**
     * 根据消息键和参数 获取消息
     * 委托给spring messageSource
     *
     * @param code 消息键
     * @param args 参数
     * @return
     */
    public static String message(String code, Object... args) {
        if (messageSource == null) {
            messageSource = SpringUtils.getBean(MessageSource.class);
        }
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    public static String message(MessageSourceResolvable resolvable) {
    	if (messageSource == null) {
            messageSource = SpringUtils.getBean(MessageSource.class);
        }
        return messageSource.getMessage(resolvable, LocaleContextHolder.getLocale());
    }

}

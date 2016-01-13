package com.lb.framework.web.filter.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * 敏感信息过滤的filter
 * 
 * @author lb
 * 
 */
public class SensitiveValueFilter implements ValueFilter {

    private SensitiveLogConfig config;
    private Pattern ignoreParamPattern;

    public SensitiveValueFilter(SensitiveLogConfig config) {
        this.config = config;
        this.ignoreParamPattern = Pattern.compile(config.getIgnoreParamPattern());
    }

    @Override
    public Object process(Object object, String name, Object value) {
        if (config == null || ignoreParamPattern == null) {
            return value;
        }
        Matcher matcher = ignoreParamPattern.matcher(name);
        if (matcher.matches()) {
            return "******";
        }
        if (!isInternalType(value)) {
            return value;
        }
        String str = value.toString();
        if (str.length() > config.getValueLengthLimit()) {
            return str.substring(0, config.getValueLengthLimit()) + "...";
        } else {
            return str;
        }
    }
    
    /**
     * 判断是否内置的基本类型
     * 
     * @param value
     * @return
     */
    private boolean isInternalType(Object value) {
        if (value instanceof Number) {
            return true;
        }
        if (value instanceof String) {
            return true;
        }
        return false;
    }

}

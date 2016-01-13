package com.lb.framework.web.filter.logic;

/**
 * 根据参数名来判断是否敏感日志的配置类
 * @author lb
 *
 */
public class SensitiveLogConfig {

    /**
     * 参数值限制的最大打印字符数的默认值
     */
    public static final int DEFAULT_VALUE_LENGTH_LIMIT = 64;

    /**
     * 默认的请求参数名的正则表达式
     */
    public static final String DEFAULT_PARAM_PATTERN = "(?i).*(password|pwd|bankCard|CardNo|bindCardNo).*";
    
    /**
     * 设置不记录请求参数名的正则表达式. 例如: (?i).*(password|pwd|sign).*
     */
    private String ignoreParamPattern = DEFAULT_PARAM_PATTERN;

    /**
     * 参数值限制的最大打印字符数
     */
    private int valueLengthLimit = DEFAULT_VALUE_LENGTH_LIMIT;
    
    
    public String getIgnoreParamPattern() {
        return ignoreParamPattern;
    }

    public void setIgnoreParamPattern(String ignoreParamPattern) {
        this.ignoreParamPattern = ignoreParamPattern;
    }

    public int getValueLengthLimit() {
        return valueLengthLimit;
    }

    public void setValueLengthLimit(int valueLengthLimit) {
        this.valueLengthLimit = valueLengthLimit;
    }
}

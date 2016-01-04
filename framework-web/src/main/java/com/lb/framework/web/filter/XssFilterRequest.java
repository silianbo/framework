package com.lb.framework.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.text.translate.AggregateTranslator;
import org.apache.commons.lang3.text.translate.CharSequenceTranslator;
import org.apache.commons.lang3.text.translate.EntityArrays;
import org.apache.commons.lang3.text.translate.LookupTranslator;

/**
 * 封装request对象
 * 
 * @author lb
 * 
 */
class XssFilterRequest extends HttpServletRequestWrapper {

    public XssFilterRequest(HttpServletRequest request) {
        super(request);
    }
    
    /**
     * 对请求参数的值做转义
     * 
     * @param value
     *            参数值
     * @return 转义后的值
     */
    public String escapeParam(String value) {
        if (value == null) {
            return null;
        }
        return CUSTOMER_ESCAPE.translate(value);
    }
    
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return escapeParam(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return null;
        }
        for (int i = 0; i < values.length; i++) {
            String vi = values[i];
            values[i] = escapeParam(vi);
        }
        return values;
    }
    
    /**
     * 自定义的转义
     */
    public static final CharSequenceTranslator CUSTOMER_ESCAPE =  new AggregateTranslator(
        // JavaScript
       new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE()),
       new LookupTranslator(
              new String[][] { 
                    {"\\", "\\\\"},
                    {"/", "\\/"}
              }),
        // HTML
        new LookupTranslator(EntityArrays.BASIC_ESCAPE()),
        new LookupTranslator(EntityArrays.APOS_ESCAPE()),
        new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE()),
        new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE())
      );
}
package com.lb.framework.web.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.WebUtils;

/**
 * web层工具类
 * 
 * @author 464281
 * @version $Id: WebUtil.java 2014-5-27 下午3:05:58 $
 */
public class WebUtil extends WebUtils {

    /**
     * 获取请求url上指定参数名对应的参数值
     * <p>
     * 没有对应的参数名则返回<code>NULL</code>，存在多个则返回第一个
     * 
     * @param request HTTP请求
     * @param name 指定参数名
     * @return 对应的参数值
     */
    public static final String getQueryParameter(HttpServletRequest request,
                                                 String name) {
        String[] values = getQueryParameterValues(request, name);
        return ArrayUtils.isEmpty(values) ? null : values[0];
    }

    /**
     * 获取请求url上指定参数名对应的参数值集合
     * <p>
     * 没有对应的参数名则返回空集合
     * 
     * @param request HTTP请求
     * @param name 指定参数名
     * @return 对应的参数值集合
     */
    public static final String[] getQueryParameterValues(HttpServletRequest request,
                                                         String name) {
        List<String> values = new ArrayList<String>();
        String queryString = request.getQueryString();

        if (StringUtils.isNotBlank(queryString)) {
            String[] queryNVs = StringUtils.split(queryString, '&');

            for (String queryNV : queryNVs) {
                if (StringUtils.isNotBlank(queryNV)) {
                    String[] parameterNV = StringUtils.split(queryString, '=');

                    if (parameterNV.length == 2
                        && StringUtils.equals(parameterNV[0], name)) {
                        values.add(parameterNV[1]);
                    }
                }
            }
        }
        return values.toArray(new String[] {});
    }

    /**
     * 获取请求URI和请求参数
     * 
     * @param request HTTP请求
     * @return 请求URI和请求参数
     */
    public static final String getRequestUriWithQueryString(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String queryString = request.getQueryString();
        if (StringUtils.isBlank(queryString)) {
            return requestUri;
        } else {
            return requestUri + '?' + queryString;
        }
    }
}

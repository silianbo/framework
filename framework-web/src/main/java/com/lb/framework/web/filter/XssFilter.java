package com.lb.framework.web.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 防御XSS的输入过滤器
 * @author lb
 */
public class XssFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        @SuppressWarnings("unchecked")
		Map<String, Object> parameterMap = request.getParameterMap();
        if(parameterMap == null || parameterMap.isEmpty()) {
            filterChain.doFilter(request, response);
            return ;
        }
        // 对输入参数做escape
        for(Entry<String, Object> entry : parameterMap.entrySet()) {
            String[] values = (String[])entry.getValue();
            for(int i=0; i<values.length; i++) {
                String vi = values[i];
                vi = StringEscapeUtils.escapeEcmaScript(vi);
                vi = StringEscapeUtils.escapeHtml4(vi);
                values[i] = vi;
            }
        }
        filterChain.doFilter(request, response);
    }

}

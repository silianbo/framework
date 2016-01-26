package com.github.sunflowerlb.framework.web.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.github.sunflowerlb.framework.web.filter.logic.XssFilterRequest;

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
        XssFilterRequest newRequest = new XssFilterRequest(request);
        filterChain.doFilter(newRequest, response);
    }

}

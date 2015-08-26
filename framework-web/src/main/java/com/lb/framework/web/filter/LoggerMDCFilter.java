package com.lb.framework.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lb.framework.web.servlet.HttpServletHolder;
import com.lb.framework.web.util.WebUtil;

/**
 * 日志MDC过滤器
 * <p>
 * 加入url和uid的mdc参数
 * 
 * @author 464281
 */
public class LoggerMDCFilter extends OncePerRequestFilter {

	/**
	 * 
	 */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
			MDC.put("url", WebUtil.getRequestUriWithQueryString(request));
			MDC.put("uid", HttpServletHolder.getUid());
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

}

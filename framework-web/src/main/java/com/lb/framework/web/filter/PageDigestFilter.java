package com.lb.framework.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 请求摘要拦截器
 * 
 * 记录请求响应时间
 * 
 * @author 464281
 * 
 */
public class PageDigestFilter extends OncePerRequestFilter {

    private static Logger logger = LoggerFactory.getLogger("PAGE-DIGEST");

    /**
     * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (logger.isInfoEnabled()) {
            long startTime = System.currentTimeMillis();
            String method = request.getMethod();
            String requestUri = request.getRequestURI();

            try {
                filterChain.doFilter(request, response);
            } finally {
                long elapseTime = System.currentTimeMillis() - startTime;
                logger.info("{} {} {} ms", requestUri, method, elapseTime);
            }

        } else {
            filterChain.doFilter(request, response);
        }
    }
}

package com.lb.framework.web.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alibaba.fastjson.JSON;
import com.github.knightliao.apollo.utils.web.IpUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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
            try {
                filterChain.doFilter(request, response);
            } finally {
                long elapseTime = System.currentTimeMillis() - startTime;
                logger.info(buildLog(request, elapseTime));
            }

        } else {
            filterChain.doFilter(request, response);
        }
    }
    
    /**
     * 记录访问摘要信息日志
     * (jsessionid)(ip)(accept)(UserAgent)(url)(method)(elapseTime)(params)(headers)(Referer)
     *
     * @param request
     */
    public static String buildLog(HttpServletRequest request, long elapseTime) {
        String jsessionId = request.getRequestedSessionId();
        String ip = IpUtils.getIpAddr(request);
        String accept = request.getHeader("accept");
        String userAgent = request.getHeader("User-Agent");
        String url = request.getRequestURI();
        String method = request.getMethod();
        String params = getParams(request);
        String headers = getHeaders(request);

        StringBuilder s = new StringBuilder();
        s.append(getBlock(jsessionId));
        s.append(getBlock(ip));
        s.append(getBlock(accept));
        s.append(getBlock(userAgent));
        s.append(getBlock(url));
        s.append(getBlock(method));
        s.append(getBlock(elapseTime));
        s.append(getBlock(params));
        s.append(getBlock(headers));
        s.append(getBlock(request.getHeader("Referer")));
        return s.toString();
    }
    
    public static String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "(" + msg.toString() + ")";
    }
    
    @SuppressWarnings("unchecked")
    protected static String getParams(HttpServletRequest request) {
		Map<String, String[]> params = request.getParameterMap();
        return JSON.toJSONString(params);
    }

    @SuppressWarnings("unchecked")
    private static String getHeaders(HttpServletRequest request) {
        Map<String, List<String>> headers = Maps.newHashMap();
        Enumeration<String> namesEnumeration = request.getHeaderNames();
        while(namesEnumeration.hasMoreElements()) {
            String name = namesEnumeration.nextElement();
            Enumeration<String> valueEnumeration = request.getHeaders(name);
            List<String> values = Lists.newArrayList();
            while(valueEnumeration.hasMoreElements()) {
                values.add(valueEnumeration.nextElement());
            }
            headers.put(name, values);
        }
        return JSON.toJSONString(headers);
    }
}

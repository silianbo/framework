package com.github.sunflowerlb.framework.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.sunflowerlb.framework.core.log.LogConst;
import com.github.sunflowerlb.framework.web.servlet.HttpServletHolder;
import com.github.sunflowerlb.framework.web.util.WebUtil;

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
        	String uid = HttpServletHolder.getUid();
			MDC.put(LogConst.URL, WebUtil.getRequestUriWithQueryString(request));
			MDC.put(LogConst.UID, uid);
			// 用于同1个controller调用多个dubbo service的时候,能共用同1个uid
			MDC.put(LogConst.IS_THREAD_PROVIDER, LogConst.TRUE);
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

}

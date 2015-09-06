package com.lb.framework.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alibaba.dubbo.rpc.RpcContext;
import com.lb.framework.core.log.LogConst;
import com.lb.framework.core.log.LogTools;
import com.lb.framework.web.servlet.HttpServletHolder;

/**
 * 设置HTTP SERVLET请求和响应持有者本地线程变量
 * 
 * @author 464281
 */
public class HttpServletHolderFilter extends OncePerRequestFilter {

	/**
	 * (non-Javadoc)
	 * 
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String uniqueId = request.getHeader(HttpServletHolder.UNIQUE_ID_HEADER_NAME);
			if(StringUtils.isBlank(uniqueId)) {
				uniqueId = LogTools.generateUID();
                RpcContext.getContext().setAttachment(LogConst.UID, uniqueId);
                HttpServletHolder.setUid(uniqueId);
            }
			HttpServletHolder.set(request, response, uniqueId);
			filterChain.doFilter(request, response);
		} finally {
			HttpServletHolder.remove();
		}
	}
}

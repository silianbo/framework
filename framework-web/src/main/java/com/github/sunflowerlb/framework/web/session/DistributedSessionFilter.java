package com.github.sunflowerlb.framework.web.session;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 分布式会话过滤器
 * <p>
 * 过滤器必须设置在所有需要使用会话的过滤器之前
 * 
 * @author lb
 * 
 * @see com.lb.framework.web.session.DistributedSessionManager
 */
public class DistributedSessionFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory
			.getLogger(DistributedSessionFilter.class);

	private DistributedSessionManager distributedSessionManager;

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		DistributedSessionRequest sessionRequest = new DistributedSessionRequest(
				request, response);
		sessionRequest.setDistributedSessionManager(distributedSessionManager);

		try {
			filterChain.doFilter(sessionRequest, response);
		} finally {
			try {
				sessionRequest.commitSession();
			} catch (DistributedSessionException e) {
				logger.error("fail to commit distributed session!", e);
			}
		}
	}

	public void setDistributedSessionManager(
			DistributedSessionManager distributedSessionManager) {
		this.distributedSessionManager = distributedSessionManager;
	}

}

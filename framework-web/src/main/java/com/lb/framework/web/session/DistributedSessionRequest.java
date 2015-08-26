package com.lb.framework.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分布式会话请求
 * <p>
 * 作为会话的桥接，持有了会话管理器和分布式会话，提供了会话的获取和提交的入口。
 * 
 * @author lb
 * 
 * @see javax.servlet.http.HttpServletRequestWrapper
 * @see com.ihome.framework.web.session.DistributedSessionManager
 * @see com.ihome.framework.web.session.DistributedSession
 */
public class DistributedSessionRequest extends HttpServletRequestWrapper {

	private static final Logger logger = LoggerFactory
			.getLogger(DistributedSessionRequest.class);

	private HttpServletResponse response;

	private DistributedSessionManager distributedSessionManager;

	private DistributedSession distributedSession;

	/** 会话ID是否初始化过，避免多次从存储中获取会话 */
	private boolean sessionIdInitialized = false;

	private String distributedSessionId;

	public DistributedSessionRequest(HttpServletRequest request,
			HttpServletResponse response) {
		super(request);
		this.response = response;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletRequestWrapper#getSession()
	 */
	@Override
	public HttpSession getSession() {
		return getSession(true);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletRequestWrapper#getSession(boolean)
	 */
	@Override
	public HttpSession getSession(boolean create) {
		// 除了满足无效返回null的规范之外，新增已提交也返回null
		if (null != distributedSession) {
			if (distributedSession.isInvalidated()
					|| distributedSession.isCommitted()) {
				return null;
			}
			return distributedSession;
		}

		// 尝试初始化一次会话id
		if (!sessionIdInitialized) {
			getRequestedSessionId();
		}

		if (null == distributedSession && create) {
			if (response.isCommitted()) {
				throw new IllegalStateException("response has committed!");
			}

			// 强制创建
			distributedSession = distributedSessionManager.createSession(this,
					response);
			distributedSessionId = distributedSession.getId();
		}

		return distributedSession;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletRequestWrapper#getRequestedSessionId()
	 */
	@Override
	public String getRequestedSessionId() {
		if (null == this.distributedSessionId && !sessionIdInitialized) {
			sessionIdInitialized = true;
			String distributedSessionId = distributedSessionManager
					.getSessionId(this, response);

			if (null != distributedSessionId) {

				// 尝试获取一次会话，确定会话ID是否是有效的
				distributedSession = distributedSessionManager.getSession(
						distributedSessionId, this, response);

				// 存在则有效
				if (null != distributedSession) {
					this.distributedSessionId = distributedSessionId;

				} else {
					logger.debug("session id does not exist in session manager!");
				}

			} else {
				logger.debug("request does not contain session id!");
			}
		}
		return distributedSessionId;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletRequestWrapper#isRequestedSessionIdFromCookie()
	 */
	@Override
	public boolean isRequestedSessionIdFromCookie() {
		return true;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletRequestWrapper#isRequestedSessionIdFromUrl()
	 */
	@Override
	public boolean isRequestedSessionIdFromUrl() {
		return false;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletRequestWrapper#isRequestedSessionIdFromURL()
	 */
	@Override
	public boolean isRequestedSessionIdFromURL() {
		return false;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletRequestWrapper#isRequestedSessionIdValid()
	 */
	@Override
	public boolean isRequestedSessionIdValid() {
		return null != distributedSession && !distributedSession.isCommitted()
				&& !distributedSession.isInvalidated();
	}

	public void commitSession() {
		if (null != distributedSession && !distributedSession.isInvalidated()) {
			if (!distributedSession.isCommitted()) {
				distributedSessionManager.commitSession(distributedSession);
			} else {
				logger.warn("distributed session has committed!");
			}
		}
	}

	public void setDistributedSessionManager(
			DistributedSessionManager distributedSessionManager) {
		this.distributedSessionManager = distributedSessionManager;
	}

}

package com.lb.framework.web.session;

import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * 默认的分布式会话管理器实现
 * <p>
 * 默认管理器实现了通用的会话管理功能，将会话存储交给分布式会话DAO
 * {@link com.ihome.framework.web.session.DistributedSessionDao}来实现
 * 
 * @author 464281
 * 
 * @see com.ihome.framework.web.session.DistributedSessionManager
 * @see com.ihome.framework.web.session.DistributedSession
 * @see com.ihome.framework.web.session.DistributedSessionDao
 */
public class DefaultDistributedSessionManager implements
		DistributedSessionManager, InitializingBean {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultDistributedSessionManager.class);

	private ServletContext servletContext;

	/** 会话域，可为<code>NULL</code>，<code>NULL</code>表示在当前域 */
	private String sessionDomain;

	/**
	 * 会话cookie名称
	 * <p>
	 * 不能为空，默认<code>IHOME_JSESSIONID</code> ，不能为J2EE规范中的'JSESSIONID'
	 */
	private String sessionCookieName = "IHOME_JSESSIONID";

	/**
	 * 是否每次提交
	 * <p>
	 * <code>TRUE</code>表示每次提交。若是每次提交，不管会话是否有变化，均会被提交
	 */
	private boolean alwaysCommit = false;

	private DistributedSessionDao distributedSessionDao;

	private Random random = new Random();

	/** 会话超时时间，单位为秒，默认1800秒 */
	private int sessionTimeout = 1800;

	/**
	 * 临时存储sessionId的token字段
	 */
	private String ihomeToken = "ihome-token";
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if (sessionTimeout <= 0) {
			throw new DistributedSessionException(
					"distributed session timeout would great than zero, sessionTimeout = "
							+ sessionTimeout);
		}
		if (StringUtils.isBlank(sessionCookieName)) {
			throw new IllegalArgumentException(
					"sessionCookieName would not be blank!");
		}
		if (StringUtils.equals("JSESSIONID", sessionCookieName)) {
			throw new IllegalArgumentException(
					"sessionCookieName would not equals 'JSESSIONID'!");
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.ihome.framework.web.session.DistributedSessionManager#getSession(java.lang.String,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public DistributedSession getSession(String sessionId,
			HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isBlank(sessionId)) {
			return null;
		}

		DistributedSession distributedSession = distributedSessionDao
				.getSession(sessionId);
		if (null == distributedSession) {
			return null;
		}

		initSession(request, response, servletContext, distributedSession,
				sessionTimeout);
		return distributedSession;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.ihome.framework.web.session.DistributedSessionManager#createSession(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public DistributedSession createSession(HttpServletRequest request,
			HttpServletResponse response) {
		String sessionId = createSessionId(request);

		DistributedSession distributedSession = new DistributedSession(
				sessionId);
		distributedSession.setCreationTime(System.currentTimeMillis());
		distributedSession.setLastAccessedTime(System.currentTimeMillis());
		distributedSession.setLastCommittedTime(System.currentTimeMillis());
		distributedSession.setNew(true);

		initSession(request, response, servletContext, distributedSession,
				sessionTimeout);

		distributedSessionDao.createSession(distributedSession);

		// 置为会话生命周期
		storeSessionId(response, sessionId, -1);
		return distributedSession;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.ihome.framework.web.session.DistributedSessionManager#getSessionId(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String getSessionId(HttpServletRequest request,
			HttpServletResponse response) {
	    // 优先取临时token的值作为sessionId
	    if(StringUtils.isNotBlank(request.getHeader(ihomeToken))) {
	        return request.getHeader(ihomeToken);
	    }
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (null != cookie) {
					String cookieName = cookie.getName();
					if (StringUtils.equals(cookieName, sessionCookieName)) {
						return cookie.getValue();
					}
				}
			}
		}
		return null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.ihome.framework.web.session.DistributedSessionManager#commitSession(com.ihome.framework.web.session.DistributedSession)
	 */
	@Override
	public void commitSession(DistributedSession distributedSession) {
		if (isNeedCommit(distributedSession)) {
			distributedSession.setLastAccessedTime(System.currentTimeMillis());
			distributedSession.setLastCommittedTime(System.currentTimeMillis());

			distributedSessionDao.updateSession(distributedSession);
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("distributed session does not need commit!");
			}
		}
		distributedSession.setCommitted(true);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.ihome.framework.web.session.DistributedSessionManager#invalidateSession
	 *      (com.ihome.framework.web.session.DistributedSession,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void invalidateSession(DistributedSession distributedSession,
			HttpServletRequest request, HttpServletResponse response) {
		distributedSessionDao.removeSession(distributedSession);

		// 清除sessionid
		storeSessionId(response, distributedSession.getId(), 0);
	}

	/**
	 * 创建会话ID
	 * <p>
	 * 根据运行时的内存、随机数等确定，但不保证在存储端不存在
	 * 
	 * @param request
	 *            HTTP请求
	 * @return 会话ID
	 */
	protected String createSessionId(HttpServletRequest request) {
		StringBuffer buffer = new StringBuffer();
		long r = hashCode() ^ Runtime.getRuntime().freeMemory()
				^ random.nextLong() ^ (((long) request.hashCode()) << 32);

		if (request.getRemoteAddr() != null) {
			r = r ^ request.getRemoteAddr().hashCode();
		}
		if (r < 0) {
			r = -r;
		}

		buffer.append(Long.toString(r, 36));
		buffer.append('!');
		buffer.append(System.currentTimeMillis());

		return buffer.toString();
	}

	protected void initSession(HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext,
			DistributedSession distributedSession, int sessionTimeout) {
		distributedSession.setServletContext(servletContext);
		distributedSession.setRequest(request);
		distributedSession.setResponse(response);
		distributedSession.setDistributedSessionManager(this);
		distributedSession.initOriginalHashCode();
		distributedSession.initMaxInactiveInterval(sessionTimeout);
	}

	/**
	 * 保存会话ID
	 * <p>
	 * 会话域存在则设置会话域
	 * 
	 * @param response
	 *            HTTP响应
	 * @param sessionId
	 *            会话ID
	 * @param expiry
	 *            会话有效期，{@link javax.servlet.http.Cookie#setMaxAge()}
	 */
	protected void storeSessionId(HttpServletResponse response,
			String sessionId, int expiry) {
		Cookie cookie = new Cookie(sessionCookieName, sessionId);
		cookie.setPath("/");

		if (StringUtils.isNotBlank(sessionDomain)) {
			cookie.setDomain(sessionDomain);
		}

		cookie.setMaxAge(expiry);
		response.addCookie(cookie);
	}

	protected boolean isNeedCommit(DistributedSession distributedSession) {
		// 被修改的会话必须提交
		if (distributedSession.isModified()) {
			return true;
		}

		// 上次提交时间超过会话超时的一半时，必须提交
		long elapsed = (System.currentTimeMillis() - distributedSession
				.getLastCommittedTime()) / 1000;
		if (elapsed > (distributedSession.getMaxInactiveInterval() / 2)) {
			return true;
		}

		// 是否每次提交
		return alwaysCommit;
	}

	public void setSessionDomain(String sessionDomain) {
		this.sessionDomain = sessionDomain;
	}

	public void setSessionCookieName(String sessionCookieName) {
		this.sessionCookieName = sessionCookieName;
	}

	public void setAlwaysCommit(boolean alwaysCommit) {
		this.alwaysCommit = alwaysCommit;
	}

	public void setDistributedSessionDao(
			DistributedSessionDao distributedSessionDao) {
		this.distributedSessionDao = distributedSessionDao;
	}

	public void setSessionTimeout(int sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

    public void setIhomeToken(String ihomeToken) {
        this.ihomeToken = ihomeToken;
    }
	
}

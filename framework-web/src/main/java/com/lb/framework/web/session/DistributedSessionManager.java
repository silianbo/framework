package com.lb.framework.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ServletContextAware;

/**
 * 分布式会话管理器
 * <p>
 * 分布式会话管理器提供了对分布式会话创建、获取、存储和删除的所有功能，同时管理会话ID的获取、存储和删除。由于会话需要</br>
 * {@link javax.servlet.ServletContext}，管理器继承了
 * {@link org.springframework.web.context.ServletContextAware}
 * 
 * <p>
 * 关于会话配置，由于会话获取不到容器会话配置，原有会话配置在分布式会话下将不再生效，包括会话的超时时间、会话的cookie配置等，</br>
 * 需要重新配置在会话管理器中。同时由于分布存储总是需要过期设置，并且会话管理器也不支持扫描会话并置为过期控制，所以分布式会话也不支持永久存储。
 * 
 * @author lb
 * 
 * @see com.lb.framework.web.session.DistributedSession
 * @see org.springframework.web.context.ServletContextAware
 * @see javax.servlet.http.HttpServletRequest
 * @see javax.servlet.http.HttpServletResponse
 */
public interface DistributedSessionManager extends ServletContextAware {

	/**
	 * 根据会话ID获取会话
	 * <p>
	 * 存储端存在该会话则返回，没有则返回<code>NULL</code>
	 * 
	 * @param sessionId
	 *            会话ID，为<code>NULL</code>时返回<code>NULL</code>
	 * @param request
	 *            HTTP请求
	 * @param response
	 *            HTTP响应
	 * @return 分布式会话
	 * @see com.lb.framework.web.session.DistributedSession
	 * @throws DistributedSessionException
	 *             当从存储端到获取出现异常时抛出
	 */
	public DistributedSession getSession(String sessionId,
			HttpServletRequest request, HttpServletResponse response);

	/**
	 * 创建会话
	 * <p>
	 * 会话创建必须保证唯一性，不唯一则抛出{@link DistributedSessionException}</br>
	 * 异常。同时，会创建新的会话ID并存储到 {@link javax.servlet.http.HttpServletResponse}中
	 * 
	 * @param request
	 *            HTTP请求
	 * @param response
	 *            HTTP响应
	 * @return 分布式会话
	 * @see com.lb.framework.web.session.DistributedSession
	 * @throws DistributedSessionException
	 *             当会话ID已经存在或存储出现异常时抛出
	 */
	public DistributedSession createSession(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 从HTTP请求中获取会话ID
	 * 
	 * @param request
	 *            HTTP请求
	 * @param response
	 *            HTTP响应
	 * @return 会话ID，不存在则返回<code>NULL</code>
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 */
	public String getSessionId(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 提交会话
	 * <p>
	 * 提交会话应该只提交一次，但实现不会进行控制，需要调用方进行控制。有修改的会话必须提交，同时需要满足会话存储端的要求
	 * 
	 * @param distributedSession
	 *            分布式会话
	 * @see com.lb.framework.web.session.DistributedSession
	 * @throws DistributedSessionException
	 *             当存储出现异常时抛出
	 */
	public void commitSession(DistributedSession distributedSession);

	/**
	 * 置会话为无效
	 * <p>
	 * 置为无效的同时，需要清除浏览器端的会话ID
	 * 
	 * @param distributedSession
	 *            分布式会话
	 * @param request
	 *            HTTP请求
	 * @param response
	 *            HTTP响应
	 * @see com.lb.framework.web.session.DistributedSession
	 * @throws DistributedSessionException
	 *             当从存储端删除出现异常时抛出
	 */
	public void invalidateSession(DistributedSession distributedSession,
			HttpServletRequest request, HttpServletResponse response);
}

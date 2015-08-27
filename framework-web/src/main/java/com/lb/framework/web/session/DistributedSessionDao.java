package com.lb.framework.web.session;

/**
 * 分布式会话DAO
 * <p>
 * 提供默认的分布式会话存储接口
 * 
 * @author 464281
 * 
 * @see com.lb.framework.web.session.DistributedSession
 */
public interface DistributedSessionDao {

	/**
	 * 根据会话ID获取会话
	 * 
	 * @param sessionId
	 *            会话ID
	 * @return 分布式会话
	 * @throws DistributedSessionException
	 *             当从存储到获取出现异常时抛出
	 */
	public DistributedSession getSession(String sessionId);

	/**
	 * 创建会话
	 * <p>
	 * 创建会话必须保证唯一性，当会话ID已经存在则应该抛出{@link DistributedSessionException}异常
	 * 
	 * @param distributedSession
	 *            分布式会话
	 * @throws DistributedSessionException
	 *             当会话ID已经存在或存储操作出现异常时抛出
	 */
	public void createSession(DistributedSession distributedSession);

	/**
	 * 更新会话
	 * 
	 * @param distributedSession
	 *            分布式会话
	 * @throws DistributedSessionException
	 *             当存储操作出现异常时抛出
	 */
	public void updateSession(DistributedSession distributedSession);

	/**
	 * 清除会话
	 * 
	 * @param distributedSession
	 *            分布式会话
	 * @throws DistributedSessionException
	 *             当从存储中删除出现异常时抛出
	 */
	public void removeSession(DistributedSession distributedSession);
}

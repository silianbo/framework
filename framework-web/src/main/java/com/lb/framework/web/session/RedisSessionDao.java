package com.lb.framework.web.session;

import com.github.knightliao.apollo.redis.RedisCacheManager;

/**
 * 基于Redis 存储的分布式会话 DAO
 * 
 * @author lb
 * 
 * @see com.lb.framework.web.session.DistributedSession
 */
public class RedisSessionDao implements DistributedSessionDao {

	// Redis缓存管理器
	protected RedisCacheManager redisCacheManager;

	// 会话放在 redis 的 key 的前缀. 和其他业务的 key 分开
	protected String sessionKeyPrefix = "sessionid.";

	@Override
	public DistributedSession getSession(String sessionId) {
		try {
			return (DistributedSession) redisCacheManager.get(sessionKeyPrefix + sessionId);
		} catch (Exception e) {
			throw new DistributedSessionException("fail to get distributed session from redis!", e);
		}
	}

	@Override
	public void createSession(DistributedSession distributedSession) {
		try {
			redisCacheManager.put(sessionKeyPrefix + distributedSession.getId(), distributedSession.getMaxInactiveInterval(), distributedSession);
		} catch (Exception e) {
			throw new DistributedSessionException("fail to store new distributed session to redis!", e);
		}
	}

	@Override
	public void updateSession(DistributedSession distributedSession) {
		try {
			redisCacheManager.put(sessionKeyPrefix + distributedSession.getId(), distributedSession.getMaxInactiveInterval(), distributedSession);
		} catch (Exception e) {
			throw new DistributedSessionException("fail to update distributed session to redis!", e);
		}
	}

	@Override
	public void removeSession(DistributedSession distributedSession) {
		try {
			redisCacheManager.remove(sessionKeyPrefix + distributedSession.getId());
		} catch (Exception e) {
			throw new DistributedSessionException("fail to remove distributed session from redis!", e);
		}
	}

	public void setRedisCacheManager(RedisCacheManager redisCacheManager) {
		this.redisCacheManager = redisCacheManager;
	}

	public void setSessionKeyPrefix(String sessionKeyPrefix) {
		this.sessionKeyPrefix = sessionKeyPrefix;
	}
}

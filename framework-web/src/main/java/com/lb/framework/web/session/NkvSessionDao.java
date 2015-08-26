package com.ihome.framework.web.session;

import com.ihome.framework.core.cache.nkv.IHomeNkvClient;

/**
 * 基于网易 NKV 存储的分布式会话 DAO
 * 
 * @author 464281
 * 
 * @see com.netease.backend.nkv.client.impl.DefaultNkvClient
 * @see com.ihome.framework.web.session.DistributedSession
 */
public class NkvSessionDao implements DistributedSessionDao {

	// nkv 客户端
	protected IHomeNkvClient nkvClient;

	// 会话放在 nkv 的 key 的前缀. 和其他业务的 key 分开
	protected String sessionKeyPrefix = "sessionid.";

	@Override
	public DistributedSession getSession(String sessionId) {
		try {
			return nkvClient.get(sessionKeyPrefix, sessionId, DistributedSession.class);
		} catch (Exception e) {
			throw new DistributedSessionException("fail to get distributed session from nkv!", e);
		}
	}

	@Override
	public void createSession(DistributedSession distributedSession) {
		try {
			nkvClient.set(sessionKeyPrefix, distributedSession.getId(), distributedSession,
					distributedSession.getMaxInactiveInterval());
		} catch (Exception e) {
			throw new DistributedSessionException("fail to store new distributed session to nkv!", e);
		}
	}

	@Override
	public void updateSession(DistributedSession distributedSession) {
		try {
			nkvClient.set(sessionKeyPrefix, distributedSession.getId(), distributedSession,
					distributedSession.getMaxInactiveInterval());
		} catch (Exception e) {
			throw new DistributedSessionException("fail to update distributed session to nkv!", e);
		}
	}

	@Override
	public void removeSession(DistributedSession distributedSession) {
		try {
			nkvClient.delete(distributedSession.getId());
		} catch (Exception e) {
			throw new DistributedSessionException("fail to remove distributed session from nkv!", e);
		}
	}

	public void setNkvClient(IHomeNkvClient nkvClient) {
		this.nkvClient = nkvClient;
	}

	public void setSessionKeyPrefix(String sessionKeyPrefix) {
		this.sessionKeyPrefix = sessionKeyPrefix;
	}
}

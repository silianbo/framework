package com.lb.framework.web.session;

import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.apache.commons.lang3.StringUtils;


/**
 * 分布式会话
 * 
 * <p>
 * 分布式会话是为了解决web集群会话共享问题，和内存式会话最大的差异在于分布式会话还存在提交的概念以及会话属性集需要支持序列化。</br>
 * 由于分布式会话需要保存到应用外存储端，必须在某个时刻之后，将会话提交到存储端，在此之后不能再修改会话，因为一次请求只会提交一</br>
 * 次会话(创建会话的请求除外)。同时，由于需要通过网络提交到存储端，序列化是必须要支持的。
 * 
 * <p>
 * 如何确定会话是否发生了变更，是提交会话需要解决的问题。可以根据会话ID和会话属性集来标识会话唯一性，当ID或属性集发生变更时，认为</br>
 * 会话发生变更。对于属性集的显式修改即增加和删除属性，很容易确定是发生了变更。对于内存式会话，隐式修改即对属性集中的对象的修改，也</br>
 * 是起作用的，对于分布式会话，通过哈希码{@link #originalHashCode} 的变动能够反映是否是隐式的属性修改，即存储到会话中的对象需要重写
 * </br>{@link #hashCode()} 方法，以达到支持隐式修改提交的问题。
 * 
 * <p>
 * 原生J2EE规范支持容器内修改会话最大存活时间
 * {@link javax.servlet.http.HttpSession#setMaxInactiveInterval(int)}</br>
 * ，但分布式会话不支持，因为容器内修改实际没有什么意义，重启后又恢复了。
 * 
 * @author 464281
 * 
 * @see javax.servlet.http.HttpSession
 * @see com.lb.framework.web.session.DistributedSessionManager
 * @see com.lb.framework.web.session.DistributedSessionRequest
 */
@SuppressWarnings("deprecation")
public class DistributedSession implements HttpSession, Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 3986201233207161701L;

	/**
	 * 会话上下文
	 * 
	 * 由于{@link javax.servlet.http.HttpSessionContext}接口属于待废弃接口，这里使用空实现
	 */
	private static final HttpSessionContext NULL_HTTP_SESSION_CONTEXT;

	/** 会话ID */
	private String id;

	/** 会话创建时间 */
	private long creationTime;

	/** 最近访问会话时间 */
	private long lastAccessedTime;

	/** 最近提交时间 */
	private long lastCommittedTime;

	/** 属性集，属性值必须实现序列化接口 */
	private Map<String, Serializable> attributes = new ConcurrentHashMap<String, Serializable>();

	/** servlet上下文，不需要序列化 */
	private transient ServletContext servletContext;

	/** 是否被置为无效，不需要序列化 */
	private transient boolean invalidated = false;

	/** 是否已经提交，不需要序列化 */
	private transient boolean committed = false;

	/** 是否是新创建的会话，不需要序列化，默认不是新创建的 */
	private transient boolean newed = false;

	/** 会话最大存活时间，以秒为单位，不需要序列化 */
	private transient int interval;

	private transient HttpServletRequest request;

	private transient HttpServletResponse response;

	private transient DistributedSessionManager distributedSessionManager;

	/** 会话是否被显式的修改过，不需要序列化 */
	private transient boolean modified = false;

	/** 会话被获取时的原始哈希码，不需要被序列化，用来支持隐式修改 */
	private transient int originalHashCode;

	static {
		NULL_HTTP_SESSION_CONTEXT = new HttpSessionContext() {

			@Override
			public HttpSession getSession(String sessionId) {
				return null;
			}

			@SuppressWarnings("unchecked")
			@Override
			public Enumeration<?> getIds() {
				return Collections.enumeration(Collections.EMPTY_LIST);
			}
		};
	}

	public DistributedSession(String id) {
		if (StringUtils.isBlank(id)) {
			throw new DistributedSessionException(
					"session id could not be blank!");
		}
		this.id = id;
	}

	/**
	 * 初始化原始哈希码
	 */
	public void initOriginalHashCode() {
		originalHashCode = hashCode();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getCreationTime()
	 */
	@Override
	public long getCreationTime() {
		checkStatus();
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getLastAccessedTime()
	 */
	@Override
	public long getLastAccessedTime() {
		checkStatus();
		return lastAccessedTime;
	}

	public void setLastAccessedTime(long lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getServletContext()
	 */
	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
	 */
	@Override
	public void setMaxInactiveInterval(int interval) {
		// 会话最大存活时间禁止被修改
	}

	public void initMaxInactiveInterval(int interval) {
		if (interval <= 0) {
			throw new DistributedSessionException(
					"distributed session's interval would great than zero, interval = "
							+ interval);
		}
		this.interval = interval;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getMaxInactiveInterval()
	 */
	@Override
	public int getMaxInactiveInterval() {
		return interval;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getSessionContext()
	 */
	@Override
	public HttpSessionContext getSessionContext() {
		return NULL_HTTP_SESSION_CONTEXT;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getAttribute(java.lang.String)
	 */
	@Override
	public Object getAttribute(String name) {
		checkStatus();
		if (null == name) {
			return null;
		}
		return attributes.get(name);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getValue(java.lang.String)
	 */
	@Override
	public Object getValue(String name) {
		return getAttribute(name);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getAttributeNames()
	 */
	@Override
	public Enumeration<?> getAttributeNames() {
		checkStatus();
		return Collections.enumeration(attributes.keySet());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getValueNames()
	 */
	@Override
	public String[] getValueNames() {
		checkStatus();
		return attributes.keySet().toArray(new String[] {});
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#setAttribute(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public void setAttribute(String name, Object value) {
		if (null == value) {
			removeAttribute(name);
			return;
		}

		checkStatus();
		if (null == name) {
			throw new DistributedSessionException(
					"session attribute name could not be null!");
		}

		// 必须实现序列化接口
		if (!(value instanceof Serializable)) {
			throw new DistributedSessionException(
					"distributed session attribute value could implement serializable interface!");
		}

		// 比对是否有改变
		Serializable oldValue = attributes.get(name);
		if (null == oldValue || !value.equals(oldValue)) {
			modified = true;
			attributes.put(name, (Serializable) value);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#putValue(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public void putValue(String name, Object value) {
		setAttribute(name, value);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#removeAttribute(java.lang.String)
	 */
	@Override
	public void removeAttribute(String name) {
		checkStatus();
		if (null == name) {
			return;
		}

		if (attributes.containsKey(name)) {
			modified = true;
			attributes.remove(name);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
	 */
	@Override
	public void removeValue(String name) {
		removeAttribute(name);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#invalidate()
	 */
	@Override
	public void invalidate() {
		checkStatus();
		distributedSessionManager.invalidateSession(this, request, response);
		invalidated = true;
	}

	public boolean isInvalidated() {
		return invalidated;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#isNew()
	 */
	@Override
	public boolean isNew() {
		checkStatus();
		return newed;
	}

	public void setNew(boolean newed) {
		this.newed = newed;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setDistributedSessionManager(
			DistributedSessionManager distributedSessionManager) {
		this.distributedSessionManager = distributedSessionManager;
	}

	public boolean isCommitted() {
		return committed;
	}

	public void setCommitted(boolean committed) {
		this.committed = committed;
	}

	private void checkStatus() {
		if (invalidated) {
			throw new IllegalStateException(
					"distributed session has invalidated!");
		}
		if (committed) {
			throw new DistributedSessionException(
					"distributed session has committed!");
		}
	}

	public long getLastCommittedTime() {
		return lastCommittedTime;
	}

	public void setLastCommittedTime(long lastCommittedTime) {
		this.lastCommittedTime = lastCommittedTime;
	}

	public boolean isModified() {
		// 显式修改和隐式修改都认为是修改了会话
		return modified || originalHashCode != hashCode();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof DistributedSession)) {
			return false;
		}

		DistributedSession other = (DistributedSession) obj;
		return id.equals(other.id) && attributes.equals(other.attributes);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hashCode = 37 * id.hashCode();
		hashCode += 67 * attributes.hashCode();
		return hashCode;
	}

}

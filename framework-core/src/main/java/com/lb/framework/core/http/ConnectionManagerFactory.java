package com.lb.framework.core.http;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author lb
 * @version V 0.1 2013-8-27 下午3:39:01
 */
public class ConnectionManagerFactory {

	private static final Logger logger = LoggerFactory.getLogger(ConnectionManagerFactory.class);

	public static PoolingClientConnectionManager createClientConnManager(ClientConfiguration config,
			HttpParams clientParams) {

		// 使用带缓存的dns解析器
		MemoryCacheDnsResolver dnsResolver = new MemoryCacheDnsResolver(); 
		DnsResolverHolder.add(dnsResolver);
		
		// 线程安全的连接池
		PoolingClientConnectionManager connManager = new PoolingClientConnectionManager(
				SchemeRegistryFactory.createDefault(), -1, TimeUnit.MILLISECONDS, dnsResolver);
		connManager.setMaxTotal(config.getMaxConnections());
		connManager.setDefaultMaxPerRoute(config.getMaxPreRoute());

		// 注册这个connManager,当有空闲的连接,给该连接设置个过时时间
		IdleConnectionReaper.registerConnectionManager(connManager);

		// SSL相关
		SSLContext sslcontext = null;
		try {
			sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { new AllTrustManager() }, null);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
		Scheme sch = new Scheme("https", 443, sf);
		connManager.getSchemeRegistry().register(sch);
		return connManager;
	}
}

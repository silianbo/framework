/**
 * 
 */
package com.github.sunflowerlb.framework.core.http;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 
 * @author lb
 */
public class AllTrustManager implements X509TrustManager {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.
	 * X509Certificate[], java.lang.String)
	 */
	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.
	 * X509Certificate[], java.lang.String)
	 */
	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
	 */
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return new java.security.cert.X509Certificate[0];
	}

}

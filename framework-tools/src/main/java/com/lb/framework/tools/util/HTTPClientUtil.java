package com.lb.framework.tools.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @version 1.0.0
 * @date 2014年8月28日
 */
public final class HTTPClientUtil {
	private static Logger log = LoggerFactory.getLogger(HTTPClientUtil.class);

	/**
	 * 发送GET类型的HTTP请求，
	 * @param url 请求的URL地址
	 * @return 返回请求连接相应的json字符串
	 */
	public static String sendGetRequest(String url) {
		CloseableHttpClient httpClient;
		if (url.startsWith("https://")) {
			httpClient = createSSLInsecureClient();
		} else {
			httpClient = HttpClients.createDefault();
		}
		CloseableHttpResponse response = null;
		String respContent = null;
		HttpGet httpGet = null;
		try {
			// 创建HttpGet请求，例如"http://localhost:9998/qq".
			httpGet = new HttpGet(url);
			log.info("Executing request:" + httpGet.getURI() + ",current Time:" + System.currentTimeMillis());
			// 执行get请求.
			response = httpClient.execute(httpGet);
			// 获取响应实体
			HttpEntity entity = response.getEntity();
			// 打印响应状态
			log.info("" + response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK && entity != null) {
				// 打印响应内容
				respContent = EntityUtils.toString(entity);
				log.info("Response content:" + respContent + ",current Time:" + System.currentTimeMillis());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 释放连接资源
			if (httpGet != null) {
				httpGet.releaseConnection();
			}
			// 关闭连接资源
			closeResource(response, httpClient);
		}
		return respContent;
	}

    /**
     * 发送GET类型的HTTP请求，
     * @param url 请求的URL地址
     * @param headerMap 设置Header
     * @return 返回请求连接相应的json字符串
     */
    public static String sendGetRequest(String url, Map<String, String> headerMap) {
        CloseableHttpClient httpClient;
        if (url.startsWith("https://")) {
            httpClient = createSSLInsecureClient();
        } else {
            httpClient = HttpClients.createDefault();
        }
        CloseableHttpResponse response = null;
        String respContent = null;
        HttpGet httpGet = null;
        try {
            // 创建HttpGet请求，例如"http://localhost:9998/qq".
            httpGet = new HttpGet(url);
            if (headerMap != null && headerMap.size() > 0) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
            }
            log.info("Executing request:" + httpGet.getURI() + ",current Time:"
                    + System.currentTimeMillis());
            // 执行get请求.
            response = httpClient.execute(httpGet);
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            // 打印响应状态
            log.info("" + response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK && entity != null) {
                // 打印响应内容
                respContent = EntityUtils.toString(entity);
                log.info("Response content:" + respContent + ",current Time:" + System.currentTimeMillis());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 释放连接资源
            if (httpGet != null) {
                httpGet.releaseConnection();
            }
            // 关闭连接资源
            closeResource(response, httpClient);
        }
        return respContent;

    }

	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 * 
	 * @param url
	 *            post请求的链接
	 * @param paramMap
	 *            请求的参数Map
	 */
	public static String sendPostRequest(String url, Map<String, Object> paramMap) {
		// 创建默认的httpClient实例.
		CloseableHttpClient httpClient = null;
		if (url.startsWith("https://")) {
			httpClient = createSSLInsecureClient();
		} else {
			httpClient = HttpClients.createDefault();
		}
		// 创建httppost
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		String respContent = null;
		// 创建参数队列
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		map2NameValuePairList(paramMap, paramList);
		UrlEncodedFormEntity uefEntity;
		try {
			httpPost = new HttpPost(url);
			uefEntity = new UrlEncodedFormEntity(paramList, "UTF-8");
			httpPost.setEntity(uefEntity);
			log.info("executing request : " + httpPost.getURI() + ",current Time:" + System.currentTimeMillis());
			response = httpClient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK && entity != null) {
					respContent = EntityUtils.toString(entity, "UTF-8");
				}
				log.info("Response content:" + respContent + ",current Time:" + System.currentTimeMillis());
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpPost != null) {
				httpPost.releaseConnection();
			}
			closeResource(response, httpClient);
		}
		return respContent;
	}

	/**
	 * 将保存POST请求的参数以及值得键值对mao转换为HTTPClient POST请求需要的List<NameValuePair>类型
	 * 
	 * @param paramMap
	 * @param paramList
	 */
	private static void map2NameValuePairList(Map<String, Object> paramMap, List<NameValuePair> paramList) {
		if (paramMap != null) {
			for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
				Object value = entry.getValue();
				if (value != null) {
					if (value instanceof String[]) {
						String[] values = (String[]) value;
						if (values.length > 0) {
							for (String str : values) {
								paramList.add(new BasicNameValuePair(entry.getKey(), str));
							}
						}
					} else {
						paramList.add(new BasicNameValuePair(entry.getKey(), value.toString()));
					}
				}
			}
		}
	}

	/**
	 * 关闭连接HTTP过程产生的资源
	 * 
	 * @param response
	 *            HTTP响应
	 * @param httpClient
	 *            HTTP客户端
	 */
	private static void closeResource(CloseableHttpResponse response, CloseableHttpClient httpClient) {
		if (response != null) {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 关闭连接,释放资源
		if (httpClient != null) {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static CloseableHttpClient createSSLInsecureClient() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}

				@Override
				public void verify(String host, SSLSocket ssl) throws IOException {
				}

				@Override
				public void verify(String host, X509Certificate cert) throws SSLException {
				}

				@Override
				public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
				}
			});
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}
}

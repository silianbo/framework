package com.github.sunflowerlb.framework.core.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.sunflowerlb.framework.tools.util.ValidateUtils;

/**
 * HttpClient的封装
 * 
 * @author lb
 */
public class HttpClientUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    private static final String ENCODING = "UTF-8";

    /**
     * 是否是使用默认的单连接的连接池
     */
    private static boolean simpleConnection = true;

    private static HttpClient client = null;

    /**
     * 使用默认的配置初始化连接池
     */
    public static void init() {
        init(new ClientConfiguration());
    }

    /**
     * 使用指定的超时等配置初始化连接池(时间单位:毫秒)
     * 
     * @param timeout
     * @param maxConns
     * @param maxPerRoute
     */
    public static void init(int timeout, int maxConns, int maxPerRoute) {
        ClientConfiguration clientConfig = new ClientConfiguration();
        if (clientConfig.isNeedTimeout()) {
            clientConfig.setConnectionTimeout(timeout);
            clientConfig.setSocketTimeout(timeout);
            clientConfig.setMaxConnections(maxConns);
            clientConfig.setMaxPreRoute(maxPerRoute);
        }
        init(clientConfig);
    }

    /**
     * 根据指定配置初始化连接池
     * 
     * @param clientConfig
     */
    public static synchronized void init(ClientConfiguration clientConfig) {
        try {
            if (client != null) {
                return;
            }
            client = HttpClientFactory.createHttpClient(clientConfig);
            logger.warn("init httpClient connection pool.timeout={},maxConns={},maxPerRoute={}", clientConfig.getConnectionTimeout(),
                    clientConfig.getMaxConnections(), clientConfig.getMaxPreRoute());

            simpleConnection = false;
        } catch (Exception ex) {
            logger.error("init http client fail!", ex);
        }
    }

    /**
     * 获取HttpClient对象
     * 
     * @return
     */
    private static HttpClient createHttpClient() {
        // 若不使用连接池，则创建1个HttpClient对象
        if (client == null) {
            return HttpClientFactory.createDefaultHttpClient();
        }
        return client;
    }

    /**
     * 关闭连接池
     */
    public static synchronized void shutdown() {
        logger.warn("shutdown httpClient connection pool.");
        if (client != null) {
            client.getConnectionManager().shutdown();
            IdleConnectionReaper.shutdown();
            DnsResolverHolder.shutdown();
            client = null;
        }
    }

    /**
     * 关闭默认的httpClient实例
     * 
     * @param httpClient
     * @param httpResponse
     */
    private static void shutdownHttpClient(HttpClient httpClient, HttpResponse httpResponse) {
        try {
            if (httpResponse != null) {
                EntityUtils.consume(httpResponse.getEntity());
            }
            if (simpleConnection) {
                httpClient.getConnectionManager().shutdown();
            }
        } catch (Exception ex) {
            logger.error("shutdownHttpClientError", ex);
        }
    }

    /**
     * httpRequest
     * 
     * @param url
     * @param method
     * @param paramMap
     * @throws IllegalArgumentException
     * @return
     */
    public static Response execute(String url, String method, Map<String, String> paramMap) {
        ValidateUtils.ensureParamNotNullEmpty("url", url);
        ValidateUtils.ensureParamNotNullEmpty("method", method);
        if (method.equalsIgnoreCase("post")) {
            return post(url, paramMap);
        } else if (method.equalsIgnoreCase("get")) {
            return get(url, paramMap);
        } else if (method.equalsIgnoreCase("delete")) {
            return delete(url, paramMap);
        }
        throw new IllegalArgumentException("method not support!method=" + method);
    }

    public static Response post(String url, String content) {
        return post(url, content, null, ENCODING);
    }

    public static Response post(String url, String content, String encoding) {
        return post(url, content, null, encoding);
    }
    
    public static Response post(String url, String content, Map<String, String> headerMap) {
        return post(url, content, headerMap, ENCODING);
    }
    
    /**
     * Post方法，body采用ByteArrayEntity
     * 
     * @param url
     * @param content
     * @param headerMap
     * @return
     */
    public static Response post(String url, String content, Map<String, String> headerMap, String encoding) {
        ValidateUtils.ensureParamNotNullEmpty("url", url);        
        Response response = new Response();
        long startTime = System.currentTimeMillis();

        HttpClient httpClient = createHttpClient();
        HttpResponse httpResponse = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            if (ValidateUtils.isNotNullEmpty(content)) {
                httpPost.setEntity(new ByteArrayEntity(content.getBytes(encoding)));
            }
            if (headerMap != null) {
                for (Map.Entry<String, String> headerEntry : headerMap.entrySet()) {
                    httpPost.addHeader(headerEntry.getKey(), headerEntry.getValue());
                }
            }
            logger.debug("http post start");

            httpResponse = httpClient.execute(httpPost);
            response.code = httpResponse.getStatusLine().getStatusCode();
            response.content = EntityUtils.toString(httpResponse.getEntity(), encoding);

            logger.debug("http post finish, response code:{} , content: {}", response.code, response.content);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.errorMsg = e.getMessage();
        } finally {
            shutdownHttpClient(httpClient, httpResponse);
        }
        response.reqTime = System.currentTimeMillis() - startTime;
        return response;
    }
    
    public static Response post(String url, Map<String, String> paramMap) {
        Map<String, String> headerMap = Collections.emptyMap();
        return post(url, paramMap, headerMap, ENCODING);
    }
    
    public static Response post(String url, Map<String, String> paramMap, String encoding) {
        return post(url, paramMap, null, encoding);
    }

    public static Response post(String url, Map<String, String> paramMap, Map<String, String> headerMap){
        return post(url, paramMap, null, ENCODING);
    }
    
    /**
     * post方法，body采用UrlEncodedFormEntity
     * 
     * @param url
     * @param paramMap
     * @param headerMap
     * @return
     */
    public static Response post(String url, Map<String, String> paramMap, Map<String, String> headerMap, String encoding) {
        ValidateUtils.ensureParamNotNullEmpty("url", url);

        Response response = new Response();
        long startTime = System.currentTimeMillis();

        HttpClient httpClient = createHttpClient();
        HttpResponse httpResponse = null;
        try {
            HttpPost httpPost = new HttpPost(url);

            if (paramMap != null) {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> paramEntry : paramMap.entrySet()) {
                    NameValuePair nameValuePair = new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue());
                    nameValuePairs.add(nameValuePair);
                }
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, encoding));
            }

            if (headerMap != null) {
                for (Map.Entry<String, String> headerEntry : headerMap.entrySet()) {
                    httpPost.addHeader(headerEntry.getKey(), headerEntry.getValue());
                }
            }
            logger.debug("http post start");

            httpResponse = httpClient.execute(httpPost);
            response.code = httpResponse.getStatusLine().getStatusCode();
            response.content = EntityUtils.toString(httpResponse.getEntity(), encoding);

            logger.debug("http post finish, response code:{} , content: {}", response.code, response.content);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.errorMsg = e.getMessage();
        } finally {
            shutdownHttpClient(httpClient, httpResponse);
        }
        response.reqTime = System.currentTimeMillis() - startTime;
        return response;
    }

    public static Response postFile(String url, Map<String, String> paramMap, String filePath) {
        ValidateUtils.ensureParamNotNullEmpty("url", url);

        Response response = new Response();
        long startTime = System.currentTimeMillis();

        HttpClient httpClient = createHttpClient();
        HttpResponse httpResponse = null;
        try {
            HttpPost httpPost = new HttpPost(url);

            FileBody bin = null;
            File file = new File(filePath);
            if (!file.exists()) {
                throw new IllegalArgumentException("fileNotExist!");
            }
            if (file != null) {
                bin = new FileBody(file);
            }
            // browser-compatible mode:only write Content-Disposition; use
            // content charset
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName(ENCODING));
            reqEntity.addPart(file.getName(), bin);

            if (paramMap != null) {
                for (Map.Entry<String, String> paramEntry : paramMap.entrySet()) {
                    StringBody item = new StringBody(paramEntry.getValue(), Charset.forName(ENCODING));
                    reqEntity.addPart(paramEntry.getKey(), item);
                }
            }
            httpPost.setEntity(reqEntity);

            logger.debug("http post start");
            httpResponse = httpClient.execute(httpPost);
            response.code = httpResponse.getStatusLine().getStatusCode();
            response.content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);

            logger.debug("http postFile finish, response code:{} , content: {}", response.code, response.content);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.errorMsg = e.getMessage();
        } finally {
            shutdownHttpClient(httpClient, httpResponse);
        }
        response.reqTime = System.currentTimeMillis() - startTime;
        return response;
    }

    public static Response delete(String url, Map<String, String> paramMap) {
        return delete(url, paramMap, ENCODING);
    }
    
    public static Response delete(String url, Map<String, String> paramMap, String encoding) {
        ValidateUtils.ensureParamNotNullEmpty("url", url);

        Response response = new Response();
        long startTime = System.currentTimeMillis();

        HttpClient httpClient = createHttpClient();
        try {
            HttpDelete del = new HttpDelete(appendParameter2Url(url, paramMap, encoding));
            httpExecute(httpClient, del, response, encoding);
            logger.debug("http delete finish, response code:{} , content: {}", response.code, response.content);
        } catch (Exception ex) {
            logger.error("httpDeleteError", ex);
            response.errorMsg = ex.getMessage();
        } finally {
            shutdownHttpClient(httpClient, null);
        }
        response.reqTime = System.currentTimeMillis() - startTime;
        return response;
    }

    public static Response get(String url, Map<String, String> paramMap) {
        return get(url, paramMap, ENCODING);
    }
    
    public static Response get(String url, Map<String, String> paramMap, String encoding) {
        ValidateUtils.ensureParamNotNullEmpty("url", url);

        Response response = new Response();
        long startTime = System.currentTimeMillis();

        HttpClient httpClient = createHttpClient();
        try {
            HttpGet get = new HttpGet(appendParameter2Url(url, paramMap, encoding));
            httpExecute(httpClient, get, response, encoding);
            logger.debug("http get finish, response code: " + response.code + ", content: " + response.content);
        } catch (Exception ex) {
            logger.error("httpGetError", ex);
            response.errorMsg = ex.getMessage();
        } finally {
            shutdownHttpClient(httpClient, null);
        }
        response.reqTime = System.currentTimeMillis() - startTime;
        return response;
    }

    public static Response getByStream(String url, Map<String, String> paramMap) {
        return getByStream(url, paramMap, ENCODING);
    }

    public static Response getByStream(String url, Map<String, String> paramMap, String encoding) {
        Response response = new Response();

        ValidateUtils.ensureParamNotNullEmpty("url", url);
        long startTime = System.currentTimeMillis();

        HttpClient httpClient = createHttpClient();
        try {
            HttpGet get = new HttpGet(appendParameter2Url(url, paramMap, encoding));
            httpExecuteByStream(httpClient, get, response);
            logger.debug("http getByStream finish, response code:{} , content: {}", response.code, response.content);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            response.errorMsg = ex.getMessage();
        } finally {
            shutdownHttpClient(httpClient, null);
        }
        response.reqTime = System.currentTimeMillis() - startTime;
        return response;
    }

    private static void httpExecute(HttpClient httpClient, HttpUriRequest request, Response resp, String encoding) throws Exception {
        HttpEntity entity = null;
        HttpResponse response;
        try {
            response = httpClient.execute(request);
            entity = response.getEntity();
            String ret = EntityUtils.toString(entity, encoding);
            resp.code = response.getStatusLine().getStatusCode();
            resp.content = ret;
        } finally {
            if (entity != null) {
                try {
                    EntityUtils.consume(entity);
                } catch (IOException e) {
                }
            }
        }
    }

    private static void httpExecuteByStream(HttpClient httpClient, HttpUriRequest request, Response resp) throws Exception {
        HttpEntity entity = null;
        HttpResponse response;
        try {
            response = httpClient.execute(request);
            entity = response.getEntity();
            InputStream is = entity.getContent();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 1024];
            int read = -1;
            while ((read = is.read(buffer)) != -1) {
                bos.write(buffer, 0, read);
            }
            resp.code = response.getStatusLine().getStatusCode();
            resp.data = bos.toByteArray();
        } finally {
            if (entity != null) {
                try {
                    EntityUtils.consume(entity);
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 将参数拼接到url后面
     * 
     * @param url
     * @param params
     */
    private static String appendParameter2Url(String url, Map<String, ?> params, String encoding) {
        if (params == null || params.isEmpty()) {
            return url;
        }
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, ?> paramEntry : params.entrySet()) {
            String value = (String) paramEntry.getValue();
            NameValuePair nameValuePair = new BasicNameValuePair(paramEntry.getKey(), value);
            nameValuePairs.add(nameValuePair);
        }
        if (!url.contains("?")) {
            url += "?";
        }
        return url + URLEncodedUtils.format(nameValuePairs, encoding);
    }
}

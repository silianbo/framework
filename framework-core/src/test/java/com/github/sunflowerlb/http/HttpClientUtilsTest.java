/**
 * 
 */
package com.github.sunflowerlb.http;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.sunflowerlb.framework.core.http.ClientConfiguration;
import com.github.sunflowerlb.framework.core.http.HttpClientUtils;
import com.github.sunflowerlb.framework.core.http.Response;

/**
 * 
 * @author lb
 */
public class HttpClientUtilsTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HttpClientUtils.init();
	}

	/**
	 * @throws Exception
	 */
	@AfterClass
	public static void afterClass() throws Exception {
		HttpClientUtils.shutdown();
	}
	
	/**
	 * Test method for {@link com.ihome.framework.core.http.HttpClientUtils#init(int, int, int)}.
	 */
	@Test
	public void testInit() {
		HttpClientUtils.init(3000, 50, 50);
	}

	/**
	 * Test method for {@link com.ihome.framework.core.http.HttpClientUtils#init(com.ihome.framework.core.http.ClientConfiguration)}.
	 */
	@Test
	public void testInitClientConfiguration() {
		HttpClientUtils.init(new ClientConfiguration());
	}

	/**
	 * Test method for {@link com.ihome.framework.core.http.HttpClientUtils#execute(java.lang.String, java.lang.String, java.util.Map)}.
	 */
	@Test
	public void testExecute() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihome.framework.core.http.HttpClientUtils#post(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testPostStringString() {
		String url = "http://jsonplaceholder.typicode.com/posts";
		String content = "xxx";
		Response resp = HttpClientUtils.post(url, content);
		Assert.assertTrue(resp.isSuc());
	}

	/**
	 * Test method for {@link com.ihome.framework.core.http.HttpClientUtils#post(java.lang.String, java.util.Map)}.
	 */
	@Test
	public void testPostStringMapOfStringString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihome.framework.core.http.HttpClientUtils#post(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testPostStringStringStringStringString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihome.framework.core.http.HttpClientUtils#post(java.lang.String, java.lang.String, java.util.Map)}.
	 */
	@Test
	public void testPostStringStringMapOfStringString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihome.framework.core.http.HttpClientUtils#post(java.lang.String, java.util.Map, java.util.Map)}.
	 */
	@Test
	public void testPostStringMapOfStringStringMapOfStringString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihome.framework.core.http.HttpClientUtils#postFile(java.lang.String, java.util.Map, java.lang.String)}.
	 */
	@Test
	public void testPostFile() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihome.framework.core.http.HttpClientUtils#delete(java.lang.String, java.util.Map)}.
	 */
	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihome.framework.core.http.HttpClientUtils#get(java.lang.String, java.util.Map)}.
	 */
	@Test
	public void testGet() {
		String url = "http://jsonplaceholder.typicode.com/posts/1";
		Map<String, String> params = new HashMap<String,String>();
		params.put("userId", "12345");
		Response resp = HttpClientUtils.get(url, params);
		Assert.assertTrue(resp.isSuc());
	}

	/**
	 * Test method for {@link com.ihome.framework.core.http.HttpClientUtils#getByStream(java.lang.String, java.util.Map)}.
	 */
	@Test
	public void testGetByStream() {
		fail("Not yet implemented");
	}
	
	/**
	 * 测试https
	 */
	@Test
	public void testHttps() {
	    String url = "https://www.baidu.com/";
	    Response resp = HttpClientUtils.get(url, null);
	    System.out.println(resp);
	    Assert.assertTrue(resp.isSuc());
	}
	
	/**
	 * 测试关闭的资源回收情况
	 * @param args
	 */
	public static void main(String[] args) {
		for(int i=0; i<3;i++) {
			HttpClientUtils.init();
			HttpClientUtils.shutdown();
		}
	}
}

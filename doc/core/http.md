## Http客户端的使用

具体的类：`HttpClientUtils`

### 连接池配置
默认的情况下，connection.timeout和socket.timeout都是60秒

#### 不使用连接池
不调用初始化方法的话，即不使用连接池。

#### 自定义连接池配置

使用HttpClientUtils.init方法初始化连接池配置。

### 具体的使用

#### get调用

		String url = "http://jsonplaceholder.typicode.com/posts/1";
		Map<String, String> params = new HashMap<String,String>();
		params.put("userId", "12345");
		Response resp = HttpClientUtils.get(url, params);
		Assert.assertTrue(resp.isSuc());

#### Response的封装

Response里面包含了
- 请求是否成功的调用：Response.isSuc()
- Http调用返回码：Response.getCode()
- Http调用返回内容：Response.getContent()
- Http调用错误信息：Response.getErrorMsg()
- Http调用时间：Response.getReqTime()










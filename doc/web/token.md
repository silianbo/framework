## 表单防重复功能

### 简介
为了提供公共的防表单防重复功能，framework简单地做了一些封装；

### 配置相关

#### Spring的最简配置

在Spring容器的主配置文件中，引入framework里面先定义好的token相关的配置

	<import resource="classpath:framework-web/ihome-framework-token.xml" />

功能说明
- 定义了id为`formTokenAspect`的bean，是token生成和校验的切面
- 定义了id为`formTokenManager`的bean，默认用`SessionTokenManager`，是将token保存在session里面

另外就是AOP拦截的配置，推荐在自己的工程添加`beans-token.xml`
主要配置一下要拦截的方法，指定aspect

	<aop:config>
		<aop:pointcut id="formTokenPointCut" expression="execution(* com.github.sunflowerlb.framework.usage.controller..*.*(..))" />

		<aop:aspect ref="formTokenAspect">
			<aop:around method="aroundAdvice" pointcut-ref="formTokenPointCut" />
		</aop:aspect>
	</aop:config>

### 代码的使用

#### 生成token
在需要生成注解的地方，加上`@FormToken(generateToken=true)`

##### @ResponseBody的方法

    @RequestMapping("/toLoginJson")
    @FormToken(generateToken=true)
    @ResponseBody
    public OpResponse toLoginJson() {
        OpResponse op = new OpResponse();
        return op;
    }
    
如果是带了`@ResponseBody`的方法，需要注意方法的returnValue必须继承framework提供的`OpResponse`对象，`OpResponse`对象目前主要有`code`、`message`、`data`、`token`这4个属性；各个产品目前使用的各种Response对象（基本都是只有`code`、`message`、`data`这3个属性）可直接继承`OpResponse`，对代码并无影响，仅仅是继承了`token`这个属性；很明显，这种情况下，会把生成的token设置到该对象的token字段

##### ModelAndView方法

    @RequestMapping("/toLogin")
    @FormToken(generateToken=true)
    public String toLogin() {
        return "testToken";
    }
    
如果方法返回的是view的名称或者ModelAndView，会把生成的token加到request.attribute，在JSP或者freemark页面可直接引用`${_ihome_form_token}`，譬如

#### 客户端使用token
客户端拿到token之后，一般是在请求的参数里面把token带上。

**约定token参数的名称为`_ihome_form_token`**

对于表单请求，一般是把token加到表单的隐藏域

	<form action="${base}/token/doLogin" method="post">
		<input type="hidden" name="_ihome_form_token" value="${_ihome_form_token}">
	</form>

对于Ajax请求，一般是将token直接加入到请求的参数里面

#### 校验token
在需要校验token的方法前面加上`@FormToken(checkToken=true)`即可在进入方法之前校验token

    @RequestMapping("/doLogin")
    @ResponseBody
    @FormToken(checkToken=true)
    public String doLogin(@RequestParam String name, @RequestParam String password){
        logger.info("doLogin, name:{}, password:{}", name, password);
        return "login success";
    }

若token校验成功，会立刻将token删除；

#### 异常情况

在需要校验token的情况，如果校验出错，会抛出异常：

- code 600：错误信息为`token不允许为空`
- code 601：错误信息为`token已过期或为无效token`

当出现异常的情况，若配置了framework提供的ExceptionHandler，则可以自定义错误信息，
可以将应用期望的错误信息配置在自己的配置文件，详见异常相关的文档。
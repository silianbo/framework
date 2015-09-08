## 异常处理相关


### 使用说明

* 框架的最上层异常类
	* ApplicationException 继承自RuntimeException

* 定义了三类父异常
	* BusinessException 业务异常的父类，建议业务级别的异常都继承它
	* PlatformException 平台异常的父类，建议平台级别的异常多继承它
 	* LogicException 逻辑异常的父类

建议应用各个模块有各自的异常基类，且该异常基类继承自上面定义的三类父异常

### 通用异常错误码

目前框架中定义了错误码的范围

- 1~999 系统通用错误代码
- 1000 以上业务系统自定义

错误码定义在类`ErrorConstant`中，应用可以继承该类，在自己的实现类中定义应用的错误码；
或者采用下面的枚举类的方式来定义错误码

**注意**

- 各种业务错误码段，由各个业务方商量后协定

### 枚举类处理异常

使用枚举类的好处：

- 错误码和错误信息都可以定义在枚举类中，代码里面可以尽量少硬编码错误信息
- 错误信息可以传递一些参数
- 代码相对比较整洁

目前框架中定义了一个接口`IErrors`，里面有2组接口，主要是定义了根据枚举返回不同错误信息和异常信息的方法：

返回错误信息

- T parse()：直接采用枚举中定义的message作为返回的信息
- T parse(Object... args)：采用枚举中定义的message作为返回信息，并传递一些参数
- T parseMsg(String message, Object...args)：采用枚举中定义的code，使用自定义的message，并可能会带上一些参数
T 这个泛型主要是方便应用指定自己的返回类型，可以直接将枚举类返回，或者对枚举类做一些重新包装；

返回异常类

- ApplicationException exp()：同上面的T parse()
- ApplicationException exp(Object... args)：同上面的T parse(Object... args)
- ApplicationException expMsg(String message, Object... args)：同上面的T parseMsg(String message, Object...args)
异常这一组，主要也是为了在抛出业务异常的时候，能够比较方便的指定一些异常信息

一些实现的demo：

- 枚举类的实现：framework-usage的`com.lb.framework.usage.exception.ErrCode`
- 枚举类的包装类：framework-core的`com.lb.framework.core.commons.OpResponse`
- 抛出异常的实例：framework-usage的`com.lb.framework.usage.controller.TestExceptionController`

使用枚举类的代码示例：

    @RequestMapping("/errcode")
    @ResponseBody
    public OpResponse errcodeUsage(HttpServletRequest req) {
    	OpResponse result = OpResponse.suc();
    	String userName = req.getParameter("userName");    
    	// 不需要参数的错误码
    	if (StringUtils.isBlank(userName)) {
    		return ErrCode.USER_NAME_EMPTY.parse();
    	}
    	// 需要参数的错误码
    	if (userName.length() > 32) {
    		return ErrCode.USER_NAME_INVALID.parse(userName);
    	}
    	// 自定义消息的错误码
    	if (userName.contains("xxx")) {
    		return ErrCode.LOGIN_FAIL.parseMsg("用户名[%s]包含敏感字符", userName);
    	}
    	return result;
    }

抛出自定义异常的代码示例：

	public void busException() {
		throw ErrCode.LOGIN_FAIL.exp();
		throw ErrCode.TEST_ERROR.exp("param1", 123);
		throw ErrCode.LOGIN_FAIL.expMsg("用户名[%s]包含敏感字符", userName);
	}

**注意**

- 建议枚举类的message写成那种只给开发排查问题的日志，真正提示给用户的信息可以结合异常拦截器，配置在1个配置文件中

### 异常拦截器

功能简介：

- 主要用于WebMVC模块
- 捕获程序中抛出的异常，记录异常信息，获取异常信息，并判断请求是JSON请求还是非JSON请求，做不同的处理
- 获取异常信息，判断异常的类型
	- 如果异常是异常框架中定义的ApplicationException的子类，则获取相应的code和message字段
	- 否则的话，code则为默认的错误码，message则为Exception的message字段 
	- **如果配置了MessageSource的话，message会从MessageSource中获取**
- 是否JSON请求的判断方式：
	- 如果配置了是RESTful的应用的话，认为所有的请求都是JSON请求
	- Ajax请求或者请求Url中带有.json或.jsonp的话，认为是JSON请求
	- 方法加了@ResponseBody注解的话，认为是JSON请求
	- 其他的情况认为是非JSON请求
- JSON请求的处理方式
	- 将从异常类获取到的code和message，以JSON形式返回
	- {"code":3003,"message":"用户名[123xx]包含敏感字符","params":{}}
- 非JSON请求的处理方式
	- 将从异常类获取到的code和message，放到ModelAndView
	- 返回默认的错误页面，可以在错误页面中获取出code和message进行展示
- JSONP的支持
	- 如果是JSON请求，且请求参数有`callback`的话，会自动将Response的内容转成JSONP的格式

默认的配置：

    <bean id="exceptionHandler" class="com.lb.framework.web.servlet.handler.IHomeHandlerExceptionResolver">
	    <property name="restService" value="false"/>
    	<property name="defaultErrorCode" value="500"/>
    	<property name="defaultErrorMessage" value="系统错误"/>
    </bean>

各个配置项：

- restService：是否为RESTful服务，若为是，则认为所有Response都是JSON格式
- defaultErrorCode：默认的错误码
- defaultErrorMessage：默认的错误信息

如果默认的配置都满足你的需求，可以使用最简配置：

    <bean id="exceptionHandler" class="com.lb.framework.web.servlet.handler.IHomeHandlerExceptionResolver">
    </bean>

完整的示例代码见：framework-usage的`com.lb.framework.usage.controller.TestExceptionController`

MessageSouce的配置

	<bean id="messageSource"
		class="org.springframework.context.support.ReloadableResourceBundleMessageSource">
		<property name="basenames">
			<list>
				<value>classpath:ErrorMessages</value>
			</list>
		</property>
		<property name="cacheSeconds" value="60"/>
	</bean>

	<bean id="exceptionHandler"
		class="com.lb.framework.web.servlet.handler.IHomeHandlerExceptionResolver">
		<property name="messageSource" ref="messageSource"/>
	</bean>

这里配置了1个ResourceBundleMessageSource实例，那么可以在classpath下放1个配置文件：`ErrorMessages_zh_CN.properties`，内容就是错误码和错误信息的映射

	3002=用户名不合法
	3003=手机号不是合法的手机号
另外，最好把`ErrorMessages_zh_CN.properties`这个文件放到DisConf上；

比较好的做法是：

- 程序里面的错误枚举，里面的message定义一些方便开发人员排查的message，然后通过MessageSource配置展示给用户看的message
- 错误抛出的时候，异常拦截器会记录异常（错误枚举中的message），并根据code去配置文件中查找出展示给用户看的错误信息
- 最终返回错误信息给用户
- 开发人员也能从日志中找到含有比较详细信息的异常

譬如：

错误枚举中定义如下：

	USER_NAME_EMPTY(3002, "userName [%s] invalid"),
	LOGIN_FAIL(3003, "phoneNum [%s] invalid"),

错误信息定义如上面例子所示；







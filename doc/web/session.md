## 分布式session

### 配置相关

#### session.properties
首先，需要准备session.properties

    session.cookieName=IHOME_FRAMEWORK_USAGE_SID
    session.timeout=1800
    session.domain=

#### Spring的最简配置

在Spring容器的主配置文件中，引入framework里面先定义好的分布式session相关的配置

	<import resource="classpath:framework-web/ihome-framework-session.xml"/>

功能说明
- 由于分布式session是保存在redis中的，所以需要Spring容器里面必须有id为`redisCacheManager`的bean
- 该配置文件中定义了id为`distributedSessionFilter`的bean，可以在filter的配置中直接加上


	<bean id="compositeFilter" class="com.lb.framework.web.filter.CompositeFilter">
		<property name="filters">
			<list>
				<bean class="com.lb.framework.web.filter.PageDigestFilter" />
				<ref bean="distributedSessionFilter" />
			</list>
		</property>
	</bean>

注意
- 若用这种方式引入，必须保证session.properties文件是放到disconf且名字为session.properties
- 不能再使用`<context:property-placeholder/>`这个配置，目前会冲突

#### web.xml配置

这里要特别说明下，对于过滤器来说，应该只拦截那些如果访问应用系统的链接，其他如js、css或者图片等不应该拦截。
同时一定要确认一点，分布式session的拦截器必须配置在所有有需要使用session的过滤器之前，比如shiro框架，这样获取到的才是分布式session。

#### 配置例子
具体的配置，可以参考一下framework-usage中的配置

### 配置详解

#### 实际bean的配置

ihome-framework-session.xml中实际上只是定义了1个bean而已，若参数不满足应用需求，应用也可以自定义
	
	<bean id="distributedSessionFilter" class="com.lb.framework.web.session.DistributedSessionFilter">
	<property name="distributedSessionManager">
		<bean class="com.lb.framework.web.session.DefaultDistributedSessionManager">
			<property name="distributedSessionDao">
				<bean class="com.lb.framework.web.session.RedisSessionDao">
					<property name="redisCacheManager">
						<ref bean="redisCacheManager" />
					</property>
				</bean>
			</property>
			<property name="sessionCookieName" value="${session.cookieName}" />
			<property name="sessionTimeout" value="${session.timeout}" />
			<property name="sessionDomain" value="${session.domain}" />
		</bean>
	</property>
	</bean>

其中的几个属性说明如下：
- session.cookieName：配置分布式sessionId在浏览器端的cookie名称，J2EE规范中的值为’JSESSIONID’，以免和servlet容器发生冲突，可依据应用系统自身需求配置；
- session.timeout：配置分布式session的超时时间，以秒为单位，默认1800秒，可依据应用系统自身需求配置；
- session.domain：分布式sessionId在浏览器端的cookie域，默认无配置，表示在当前域，可依据应用系统自身需求配置；

### 注意事项

#### 会话对象可序列化

由于分布式session需要将会话存储到redis，会话中的对象必须是可序列化的，需要实现Serializable接口。


## Validator的使用

### 简介
为了方便大家的开发，不需要在方法里面写过多的参数校验方法，引入Hibernate-Validator来方便大家的开发。

关于Hibernate-Validator

- Bean Validation 1.1(JSR 349) - defines a metadata model and API for entity and method validation
- Hibernate Validator is the reference implementation of this JSR 349

不使用框架之前，可能都需要这样检验参数：

	public void updateLoginPassword(Long customerId, String oldPassword,
			String newPassword) {
		if(customerId == null || oldPassword == null || newPassword == null){
			log.warn("PasswordServiceImpl.updateLoginPassword, customerId or oldPassword or newPassword can't null, customerId[{}], oldPassword[{}], newPassword[{}]",
					customerId, oldPassword, newPassword);
			throw new CustomerException(ErrorConstant.EMPTY, "customerId or oldPassword or newPassword can't null");
		}
		// 正式业务逻辑...
	}

使用Validator时候，参数的检验可以通过注解来声明，方法可以更简短：

	public void updateLoginPassword(@NotNull Long customerId, @NotEmpty String oldPassword,
	@NotNull String newPassword) {
		// 正式业务逻辑...
	}

### 配置相关

#### Spring的最简配置

在Spring容器的主配置文件中，引入framework里面先定义好的validator相关的配置

	<import resource="classpath:framework-core/ihome-framework-validator.xml"/>

功能说明
- 定义了id为`validatorAspect`的bean

另外就是AOP拦截的配置，推荐在自己的工程添加`beans-validator.xml`
主要配置一下要拦截的方法，指定aspect

	<aop:config>
		<aop:pointcut id="pointCut"
			expression="execution(* com.github.sunflowerlb.framework.usage.service..*.*(..))" />
		<aop:aspect ref="validatorAspect">
			<aop:around method="aroundAdvice" pointcut-ref="pointCut" />
		</aop:aspect>
	</aop:config>

### 代码的使用

目前注解的支持

- 方法参数上
- domain的属性

特别的注解@Valid

- 主要用于级联地传递约束
- 集合类型的属性，也是支持级联约束的；

### 自定义注解
//To be done

可以我先给个范例，大家来补充

### group的使用

//To be done

group的概念：同1个domain的约束，可能在不同的场景会不一样

不清楚是否会有这种需求，但估计会有，有的话可以通过加多1个注解来实现

### 错误消息自定义

//To be done

1. 直接写在注解上
2. 注解上写上errorCode，错误信息定义在properties文件(待调研)

### 目前已支持的注解

参考：`ihome-framework-usage`的`com.ihome.framework.usage.service.inner.IUsageService`

目前规范定义的注解：
[Bean Validation constraints](http://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#validator-defineconstraints-spec)

HibernateValidator自定义的注解：[HV Custom constraints](http://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#validator-defineconstraints-hv-constraints)

### 待确定的

目前只支持使用在方法参数的拦截，方法的返回值是否需要检验？

Fail fast mode是否要使用？对于方法，就是检验到第一个参数不符合就抛异常？

### 使用的注意事项

方法参数的约束

- 只能定义在实例方法上，静态方法不支持
- 注解最好定义在接口上，接口和实现都定义了注解的话，会抛运行时异常

domain的约束

- 父类属性的约束是会继承的
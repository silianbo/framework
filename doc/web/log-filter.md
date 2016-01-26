## 日志支持


### mdc日志支持
为支持有效跟踪和分析日志，日志mdc中加入了uid全局统一id和url，使用如下：

1.增加两个filter(HttpServletHolderFilter和LoggerMDCFilter)：

	<bean id="compositeFilter" class="com.github.sunflowerlb.framework.web.filter.CompositeFilter">
		<property name="filters">
			<list>
				<bean class="com.github.sunflowerlb.framework.web.filter.HttpServletHolderFilter"/>
				<bean class="com.github.sunflowerlb.framework.web.filter.LoggerMDCFilter"/>
			</list>
		</property>
	</bean>


注意

- LoggerMDCFilter必须配置在HttpServletHolderFilter后面。

2.修改Logback.xml：

Pattern标签增加%X{uid}和%X{url}，示例如下：

    <pattern>%date [%thread] [%X{uid} - %X{url}] %-5level %-2logger{20} - %msg%n</pattern>


### 请求摘要日志

对于web系统，可以记录请求摘要日志，使用如下：
1.配置增加PageDigestFilter：

	<bean id="compositeFilter" class="com.github.sunflowerlb.framework.web.filter.CompositeFilter">
		<property name="filters">
			<list>
				<bean class="com.github.sunflowerlb.framework.web.filter.PageDigestFilter" />
				<bean class="com.github.sunflowerlb.framework.web.filter.HttpServletHolderFilter"/>
				<bean class="com.github.sunflowerlb.framework.web.filter.LoggerMDCFilter"/>
			</list>
		</property>
	</bean>

注意：PageDigestFilter必须配置在HttpServletHolderFilter和LoggerMDCFilter之前。

2.修改logback.xml

	<appender name="PAGE-DIGEST-APPENDER"  class="ch.qos.logback.core.rolling.RollingFileAppender">
		<File>${log.base}/page-digest.log</File>
		<rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
			<FileNamePattern>${log.base}/page-digest_%d{yyyy-MM-dd}.%i.log</FileNamePattern>
			<timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
				<maxFileSize>10mb</maxFileSize>
			</timeBasedFileNamingAndTriggeringPolicy>
		</rollingPolicy>
		<encoder>
			<pattern>%date [%thread] [%X{uid} - %X{url}] - %msg%n</pattern>
		</encoder>
	</appender>
	<logger name="PAGE-DIGEST" additivity="false">
		<level value="INFO" />
	</logger>


### 日志收集到kafka

由于服务众多，为了方便以后排查问题，需要将所有服务的日志统一收集到kafka；

各服务只需要修改一下logback.xml中的配置，加入下面一段配置，即可将程序打印的日志输出到kafka中；

	<appender name="kafkaAppender"
		class="com.github.danielwegener.logback.kafka.KafkaAppender">
		<encoder
			class="com.github.danielwegener.logback.kafka.encoding.PatternLayoutKafkaMessageEncoder">
			<layout class="ch.qos.logback.classic.PatternLayout">
				<pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n
				</pattern>
			</layout>
		</encoder>
		<topic>logs</topic>
		<keyingStrategy
			class="com.github.danielwegener.logback.kafka.keying.RoundRobinKeyingStrategy" />
		<deliveryStrategy
			class="com.github.danielwegener.logback.kafka.delivery.AsynchronousDeliveryStrategy">
		</deliveryStrategy>
		<producerConfig>bootstrap.servers=10.166.224.97:9092</producerConfig>
		<appender-ref ref="stdout"></appender-ref>
	</appender>

	<root>
		<level value="info" />
		<appender-ref ref="stdout" />
		<appender-ref ref="kafkaAppender" />
	</root>

目前配置中的10.166.224.97:9092为开发环境的1个kafka结点；以后生产环境会搭建相应的集群；

KafkaAppender是采用了开源的实现：https://github.com/danielwegener/logback-kafka-appender












## DirMonitorServiceUtil 文件目录监控

### 简介
Disconf提供两种配置方式：
一、利用注解方式指定配置文件以及配置项与配置类的对应关系，配置项的更改由disconf自动加载更新配置。
弊端：1、要求必须存在配置文件，否则应用无法启动；2、代码中显式依赖disconf。
好处：简单方便
二、利用spring的xml配置指定disconf文件，以及配置项的对应关系。
弊端：1、配置无法自动更新。
好处：1、可以不用存在disconf配置文件，单独存在配置类，提供默认配置。
2、适用于框架开发，提供spring默认配置文件，应用需要时，引入对应的xml配置。

为了提供第二种方式的配置自动更新能力，封装了jdk的WatchService，提供工具类，方便大家开发。

### 框架开发示例
#### 1、框架提供xml默认配置
框架提供默认spring的xml配置Disconf，具体参考[ihome-framework-log.xml](/gitbucket/platform/ihome-framework/blob/master/framework-core/src/main/resources/framework-core/ihome-framework-log.xml)

	
#### 2、实现配置自动更新
##### 提供日志配置的BeanFactory 
BeanFacotry应该优先从spring的applicationContext中获取ihome-framework-log.xml配置的配置对象。
如果没有，可以提供默认配置对象或者手动加载配置文件。添加文件目录监控。
##### 实现文件监控
继承AbstractFileMonitor，实现对感兴趣的文件变更时，手动重新加载配置文件。 
参考[Factory](/gitbucket/platform/ihome-framework/blob/master/framework-core/src/main/java/com/ihome/framework/core/log/SensInfoLogCryptoConfig.java)和[SensInfoLogCryptoConfigFileMonitor](/gitbucket/platform/ihome-framework/blob/master/framework-core/src/main/java/com/ihome/framework/core/log/SensInfoLogCryptoConfig.java)
以上两个皆为[SensInfoLogCryptoConfig](/gitbucket/platform/ihome-framework/blob/master/framework-core/src/main/java/com/ihome/framework/core/log/SensInfoLogCryptoConfig.java)的内部类。

### 应用使用方法
1、配置方式
应用如果需要在运行期更改配置或者更改默认配置
可以在应用的spring的xml配置文件中加入以下import信息:
`	<import resource="classpath*:framework-core/ihome-framework-log.xml"/>`
在disconf中提供senslog.properties配置就可以。

**注意**：import的时候，必须写classpath*，才能够引用到框架jar包中的spring默认配置

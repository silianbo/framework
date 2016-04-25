## FTP&SFTP的使用


### FTP相关

#### ftp.properties
首先，需要准备ftp.properties

	ftp.host=ftp_ip
	ftp.port=ftp_port
	ftp.username=ftp_username
	ftp.password=ftp_pwd
	ftp.controlencoding=UTF-8

#### Spring的最简配置

在Spring容器的主配置文件中，引入framework里面先定义好的ftp相关的配置

	<import resource="classpath:framework-core/ihome-framework-ftp.xml"/>

功能说明
- 定义了id为`ftpClientService`的bean，程序里面可以直接引用


### 接口的使用

目前封装了upload和download相关的操作

	public void download(String remoteDirectory, final String fileName, String localDirectory)
	public void upload(String localDirectory, String fileName,String remoteDirectory)




### SFTP相关

#### sftp.properties
首先，需要准备sftp.properties

    sftp.host=sftpip
    sftp.port=sftpport
    sftp.username=user
    sftp.password=pwd
    sftp.timeout=5000
    sftp.privatekey=/home/appops/keys/zsy_sit_key.private
    sftp.privatekeypassphrase=

#### Spring的最简配置

在Spring容器的主配置文件中，引入framework里面先定义好的sftp相关的配置

	<import resource="classpath:framework-core/ihome-framework-sftp.xml"/>

功能说明
- 定义了id为`sFtpClientService`的bean，程序里面可以直接引用


### 接口的使用

目前封装了upload和download相关的操作

	public void download(String remoteDirectory, final String fileName, String localDirectory)
	public void upload(String localDirectory, String fileName,String remoteDirectory)












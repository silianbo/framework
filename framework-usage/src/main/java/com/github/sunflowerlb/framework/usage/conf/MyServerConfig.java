package com.github.sunflowerlb.framework.usage.conf;

import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;

/**
 * disConf相关的配置
 */
@Service
@DisconfFile(filename = "myserver.properties")
public class MyServerConfig {

    private String ip;

    private int port;

    private boolean online;
    
    private double score;

	/**
	 * @return the ip
	 */
    @DisconfFileItem(name = "test.ip")
	public String getIp() {
		return ip;
	}

	/**
	 * @return the port
	 */
    @DisconfFileItem(name = "test.port")
	public int getPort() {
		return port;
	}

	/**
	 * @return the online
	 */
    @DisconfFileItem(name = "test.online")
	public boolean isOnline() {
		return online;
	}

	/**
	 * @return the score
	 */
    @DisconfFileItem(name = "test.score")
	public double getScore() {
		return score;
	}
    
    
}

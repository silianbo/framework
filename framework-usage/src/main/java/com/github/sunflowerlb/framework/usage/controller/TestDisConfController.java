/**
 * 
 */
package com.github.sunflowerlb.framework.usage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.sunflowerlb.framework.usage.conf.MyServerConfig;

@Controller
@RequestMapping("/disConf")
public class TestDisConfController {

	private static final Logger logger = LoggerFactory.getLogger(TestDisConfController.class);
	
	@Autowired
	MyServerConfig myServerConfig;
	
	@RequestMapping("/index")
	public String justTest() {
		logger.info("myServerConfig.ip {}", myServerConfig.getIp());
		logger.info("myServerConfig.port {}", myServerConfig.getPort());
		logger.info("myServerConfig.score {}", myServerConfig.getScore());
		logger.info("myServerConfig.online {}", myServerConfig.isOnline());
		return "index";
	}
}

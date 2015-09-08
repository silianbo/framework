package com.lb.framework.usage.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lb.framework.usage.service.IDubboTestService;

@Service(value = "dubboTestService")
public class DubboTestService implements IDubboTestService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void providerTest() {
		logger.info("Test dubbo trace.");
		System.err.println("providerTest");
	}

}

package com.github.sunflowerlb.framework.usage.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.sunflowerlb.framework.usage.service.ITestFilterService;

@Service
public class TestFilterService implements ITestFilterService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void providerTest() {
		logger.info("Test dubbo filter trace.");
		System.err.println("providerTest");
	}

}

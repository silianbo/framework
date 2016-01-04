package com.lb.framework.usage.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lb.framework.usage.service.IDubboTestService;
import com.lb.framework.usage.service.ITestFilterService;

@Service(value = "dubboTestService")
public class DubboTestService implements IDubboTestService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "myTestFilterService")
	private ITestFilterService testFilterService;
	
	@Override
	public void providerTest() {
		logger.info("Test dubbo trace.");
		testFilterService.providerTest();
		System.err.println("providerTest");
	}

}

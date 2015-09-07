package com.lb.framework.usage.service.impl;

import org.springframework.stereotype.Service;

import com.lb.framework.usage.service.IDubboTestService;

@Service(value = "myDubboTestService")
public class DubboTestService implements IDubboTestService {

	@Override
	public void providerTest() {
		System.err.println("providerTest");
	}

}

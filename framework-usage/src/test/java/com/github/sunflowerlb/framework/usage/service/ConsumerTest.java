package com.github.sunflowerlb.framework.usage.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.rpc.RpcContext;
import com.github.sunflowerlb.framework.core.log.LogConst;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations="classpath:application-consumer.xml")
public class ConsumerTest {

	@Resource(name = "myDubboTestService")
	private IDubboTestService dubboTestService;
	
	@Test
	public void testConsumerService() {
		dubboTestService.providerTest();
		RpcContext.getContext().getAttachment(LogConst.UID);
	}
}

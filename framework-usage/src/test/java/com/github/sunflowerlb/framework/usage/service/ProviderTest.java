package com.github.sunflowerlb.framework.usage.service;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations="classpath:application-provider.xml")
public class ProviderTest {
	
	@Test
	public void testExportService() {
		try {
			System.out.println("test export service .");
			TimeUnit.HOURS.sleep(3);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

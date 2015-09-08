/**
 * 
 */
package com.lb.framework.usage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lb.framework.usage.service.IDubboTestService;

/**
 * 
 * @author lb
 */
@Controller
@RequestMapping("/dubbo")
public class TestDubboController {

	@Resource(name = "myDubboTestService")
	private IDubboTestService dubboTestService;

    @RequestMapping("/test")
    @ResponseBody
    public void plist() {
    	dubboTestService.providerTest();
    	dubboTestService.providerTest();
    	dubboTestService.providerTest();
    }
}

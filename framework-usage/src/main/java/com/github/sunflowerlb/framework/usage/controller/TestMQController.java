package com.github.sunflowerlb.framework.usage.controller;
/**
 * 
 *//*
package com.lb.framework.usage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mq")
public class TestMQController {

    @Autowired
	MQProducer mqProducer;

	private static final String QUEUE_NAME = "framework_usage";
	
	*//**
	 * 测试rabbitMQ的生产者功能
	 * @param req
	 * @return
	 *//*
	@RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
	@ResponseBody
	public String sendMsg(HttpServletRequest req) {
		String message = req.getParameter("message");
		mqProducer.sendMessage(message, QUEUE_NAME);
		return "success";
	}

}
*/
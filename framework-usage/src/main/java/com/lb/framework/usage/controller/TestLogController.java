/**
 * 
 */
package com.lb.framework.usage.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lb.framework.core.log.Log;

/**
 * 
 * @author lb
 */
@Controller
@RequestMapping("/log")
public class TestLogController {

	private static Logger logger = LoggerFactory.getLogger(TestLogController.class);

	@RequestMapping("/testLog")
	public String testLog() {
		// 日志规范 [operation][message],{key1=value1,key2=value2,key3=value3}

		// 1、普通的日志打印, op对应[operation] msg对应[message]
		logger.info(Log.op("SystemInit").msg("System init suc.").toString());

		// 2、普通的日志打印,需要带上一些参数, kv对应上面的{key1=value1}
		logger.info(Log.op("createOrder").msg("create order suc.").kv("orderNum", "11111").kv("user", "dongdong")
				.toString());

		// 3、普通的日志打印,带上多个参数,用map封装起来
		Map<String, Object> keyValues = new HashMap<String, Object>();
		keyValues.put("account", "zhengxiaohong");
		keyValues.put("loginIp", "10.123.124.237");
		logger.info(Log.op("userLogin").msg("new user login").kvs(keyValues).toString());
		return "index";
	}

	@RequestMapping("/testExp")
	public String testExp() {
		// 异常日志规范 [operation][message],{key1=value1,key2=value2,key3=value3} {errorStack:xxx}

		// 1、异常的打印
		try {
			throw new IOException("xxxx");
		} /*catch (IOException ex) {
			throw new RuntimeException("find a ioException", ex);
		}*/ catch (Exception ex) {
            logger.error(Log.op("dbError").msg("write db error").kv("orderNum", "11111").toString(), ex);
		}
		return "index";
	}
}

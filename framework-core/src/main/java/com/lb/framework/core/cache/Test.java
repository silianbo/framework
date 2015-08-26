package com.lb.framework.core.cache;


import java.util.ArrayList;
import java.util.List;

import com.github.knightliao.apollo.redis.RedisCacheManager;
import com.github.knightliao.apollo.redis.RedisClient;
import com.github.knightliao.apollo.redis.config.RedisHAClientConfig;

public class Test {

	public static void main(String[] args) {
		RedisHAClientConfig redisConfig1 = new RedisHAClientConfig();
		redisConfig1.setRedisServerHost("192.168.68.110");
		
		RedisHAClientConfig redisConfig2 = new RedisHAClientConfig();
		redisConfig2.setRedisServerHost("192.168.68.112");
		
		RedisClient redisClient1 = new RedisClient(redisConfig1);
		RedisClient redisClient2 = new RedisClient(redisConfig2);
		
		List<RedisClient> clients = new ArrayList<RedisClient>();
		clients.add(redisClient1);
		clients.add(redisClient2);
		
		RedisCacheManager redisMgr = RedisCacheManager.of(clients).buildRetryTimes(1)
		         .buildEvictorCheckPeriodSeconds(10).buildEvictorDelayCheckSeconds(5).buildEvictorFailedTimesToBeTickOut(3);

		 redisMgr.init(); // 如需要启动失效自动剔除恢复检测器，请务必手工调用init方法。
		 
		 redisMgr.put("111111", "88888");
		 
		 System.err.println("result:" + redisMgr.get("111111"));

	}

}

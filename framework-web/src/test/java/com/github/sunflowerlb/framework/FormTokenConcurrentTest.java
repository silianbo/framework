package com.github.sunflowerlb.framework;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.knightliao.apollo.redis.RedisCacheManager;
import com.github.knightliao.apollo.redis.RedisClient;
import com.github.knightliao.apollo.redis.config.RedisHAClientConfig;
import com.github.sunflowerlb.framework.web.form.RedisTokenManager;
import com.github.sunflowerlb.framework.web.form.TokenManager;
import com.github.sunflowerlb.framework.web.servlet.HttpServletHolder;
import com.github.sunflowerlb.framework.web.session.DefaultDistributedSessionManager;
import com.github.sunflowerlb.framework.web.session.DistributedSession;
import com.github.sunflowerlb.framework.web.session.RedisSessionDao;
import com.google.common.collect.Lists;

/**
 * token防重的并发测试
 * 
 * @author lb
 */
public class FormTokenConcurrentTest {

    public static final Logger logger = LoggerFactory.getLogger(FormTokenConcurrentTest.class);

    public static final ConcurrentHashMap<String, Boolean> map = new ConcurrentHashMap<>();
    public static final AtomicInteger counter = new AtomicInteger();
    
    /**
     * 模拟1个线程去生成token，2个线程去做check
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {

    	RedisHAClientConfig redisHAClientConfig = new RedisHAClientConfig();
        redisHAClientConfig.setCacheName("default");
        redisHAClientConfig.setRedisServerHost("121.199.61.42");
        redisHAClientConfig.setRedisServerPort(6379);
        redisHAClientConfig.setTimeout(20000);
        redisHAClientConfig.setRedisAuthKey("");
        
        RedisClient redisClient = new RedisClient(redisHAClientConfig);
        
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setClientList(Lists.newArrayList(redisClient));
        redisCacheManager.setRetryTimes(3);
        redisCacheManager.setEvictorDelayCheckSeconds(300);
        redisCacheManager.setEvictorCheckPeriodSeconds(30);
        redisCacheManager.setEvictorFailedTimesToBeTickOut(6);
        
        
        DefaultDistributedSessionManager sessionManager = new DefaultDistributedSessionManager();
        RedisSessionDao sessionDao = new RedisSessionDao();
        sessionDao.setRedisCacheManager(redisCacheManager);
        
        sessionManager.setDistributedSessionDao(sessionDao);
        
        FakeHttpServletRequest request = new FakeHttpServletRequest();
        FakeHttpServletResponse response = new FakeHttpServletResponse();
        DistributedSession session = sessionManager.createSession(request, response);
        HttpServletHolder.set(request, response);
        request.setSession(session);
        
        //TokenManager tokenManager = new SessionTokenManager();
        RedisTokenManager tokenManager = new RedisTokenManager();
        tokenManager.setRedisCacheManager(redisCacheManager);
        
        String token = tokenManager.newToken();
        System.out.println(token);
        
        for (int i = 0; i < 100; i++) {
            token = tokenManager.newToken();
            Thread t1 = new Thread(new TokenChecker(tokenManager, token, request, response));
            Thread t2 = new Thread(new TokenChecker(tokenManager, token, request, response));
            t1.start();
            t2.start();
        }
        Thread.sleep(3000);
        System.out.println(counter.get());
        System.exit(0);
    }
    
    static class TokenChecker implements Runnable {
        TokenManager tokenManager;
        String token;
        HttpServletRequest request;
        HttpServletResponse response;
        
        public TokenChecker(TokenManager tokenManager, String token, HttpServletRequest request, 
                HttpServletResponse response) {
            this.tokenManager = tokenManager;
            this.token = token;
            this.request = request;
            this.response = response;
        }

        @Override
        public void run() {
            HttpServletHolder.set(request, response);
            Boolean result = tokenManager.checkAndDelToken(token);
            synchronized (map) {
                if(map.containsKey(token)) {
                    Boolean preResult = map.get(token);
                    if(result.equals(preResult)) {
                        throw new RuntimeException("出现token重复了~~");
                    } else {
                        counter.incrementAndGet();
                    }
                } else {
                    map.put(token, result);                    
                }
            }
            logger.info("token {} check result:{}", token, result);
        }
    }
}



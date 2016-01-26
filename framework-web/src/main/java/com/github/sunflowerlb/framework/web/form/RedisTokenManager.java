package com.github.sunflowerlb.framework.web.form;

import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.knightliao.apollo.redis.RedisCacheManager;
import com.github.sunflowerlb.framework.core.exception.ApplicationException;
import com.github.sunflowerlb.framework.core.exception.PlatformException;
import com.github.sunflowerlb.framework.core.log.Log;

/**
 * 与redis的token管理器
 * @author lb
 * 
 */
public class RedisTokenManager extends AbstractTokenManager {

    private final static Logger logger = LoggerFactory.getLogger(RedisTokenManager.class);

    //Redis缓存管理器
    @Resource
    private RedisCacheManager redisCacheManager;
    
    private static final String TOKEN_PREFIX = "token_";
    
    private static final String EMPTY_STR = "";

    // 放到Redis中的token的有效期,默认3分钟
    private static final int DEFAULT_EXPIRE = 180;

    // nkv插入的重试时间
    private static final int RETRY_TIME = 3;

    @Override
    public String newToken() {
        String token = null;
        // session放在Redis中,是需要全局去重的
        // 这里分配token的时候,如果分配失败(重复或者网络问题等)的话,默认重试3次,重试3次失败则随便分配1个
        for (int i = 0; i < RETRY_TIME; i++) {
            if (token != null) {
                break;
            }
            try {
                token = UUID.randomUUID().toString();
                redisCacheManager.put(TOKEN_PREFIX + token, DEFAULT_EXPIRE, EMPTY_STR);
            } catch (PlatformException ex) {
                logger.warn(Log.op("newTokenFail").msg("newToken fail").toString(), ex);
                token = null;
            }
        }
        if (token == null) {
            token = UUID.randomUUID().toString();
        }
        return token;
    }

    @Override
    public boolean checkToken(String token) {
        return redisCacheManager.get(TOKEN_PREFIX + token) != null;
    }

    @Override
    public synchronized boolean checkAndDelToken(String token) {
        if (checkToken(token)) {
            try {
            	redisCacheManager.remove(TOKEN_PREFIX + token);
            } catch (ApplicationException ex) {
                logger.warn(Log.op("deleteTokenFail").msg("delete token fail").toString(), ex);
                return false;
            }
            return true;
        }
        return false;
    }

	public void setRedisCacheManager(RedisCacheManager redisCacheManager) {
		this.redisCacheManager = redisCacheManager;
	}

}

package com.lb.framework.web.form;

public interface TokenManager {

	/**
     * 分配新的token
     * @return
     */
    String newToken();

    /**
     * 检查token是否存在，存在返回true，不存在返回false
     * @param token
     * @return
     */
    boolean checkToken(String token);
    
    /**
     * 检查token是否存在，如果存在则删除并返回true，不存在则返回false
     * @param token
     * @return
     */
    boolean checkAndDelToken(String token);
}

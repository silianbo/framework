/**
 * 
 *//*
package com.lb.framework.usage.exception;

import java.sql.SQLException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lb.framework.core.exception.BusinessException;

*//**
 * 
 * 异常规范里面的代码示例
 * 
 * @author lb
 *//*
public class StandardDemoCode {
    private static final Logger logger = LoggerFactory.getLogger(StandardDemoCode.class);

    *//**
     * 吞掉异常的情况
     *//*
    @Test
    public void eatException() {
        try {
            // ..some code that throws SQLException
            throw new SQLException("connection fail");
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage(), ex.getCause());
        }
    }

    @Test
    public void updatePersion() throws SQLException {
        // ..some code that throws SQLException
        throw new SQLException("connection fail");
    }

    public void duplicateException() {
        try {
            // ..some code that throws SQLException
            throw new SQLException("connection fail");
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
    
    @Test
    public void retrieveObjectById(){
        try{
            //…抛出 IOException 的代码调用
            //…抛出 SQLException 的代码调用
            String[] arrys = new String[12];
            System.out.println(arrys[13]);
        }catch(Exception e){
            throw new RuntimeException("Exception in retieveObjectById", e);
        }
    }
    
    *//**
     * 登陆
     * @param username
     * @param password
     * @throws BusinessException
     *//*
    @Test
    public void login(String username, String password) {
        if(username == null || password == null) {
             throw new BusinessException("...");
        }
    }
}
*/
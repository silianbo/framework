/**
 * 
 */
package com.lb.framework.usage.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.lb.framework.usage.domain.Product;
import com.lb.framework.usage.exception.ErrCode;
import com.lb.framework.usage.exception.FrameworkUsageException;
import com.lb.framework.usage.service.inner.TestService;

/**
 * 
 * @author lb
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestService testService;

    /**
     * 测试默认情况下,不配置httpMessageConvert的情况下,会如何返回 我印象中，好像依赖里面加上json的库的话,会默认转成json
     * 
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<Product> plist() {
        List<Product> list = Lists.newArrayList();
        list.add(new Product(111, "pone"));
        list.add(new Product(222, "ptwo"));
        return list;
    }

    /**
     * 测试非框架的异常
     */
    @RequestMapping("/exception")
    @ResponseBody
    public void exception() {
        throw new NullPointerException("null pointer excep");
        // throw new IllegalArgumentException("please input your name");
    }

    /**
     * 测试框架异常
     */
    @RequestMapping("/customException")
    @ResponseBody
    public void customException() {
        throw new FrameworkUsageException("it is a framework usage exception");
    }

    /**
     * 处理当前Controller的异常
     * 
     * @ExceptionHandler(Throwable.class)
     * @param t
     * @return
     */
    @ResponseBody
    public String handleThrowable(Throwable t) {
        // 异常处理里面如果没有cache异常的话,最终就是把异常交给Tomcat处理了~
        System.out.println("TestController.handleThrowable...but will throw new Exception");
        throw new RuntimeException(t);
        // return "TestController.handleThrowable:" + t.getMessage();
    }

    /**
     * 测试分布式session
     * 
     * @param request
     * @return
     */
    @RequestMapping("/testSession")
    public String testSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println(session);

        session.setAttribute("name", "zhengxiaohong");
        session.setAttribute("exception", ErrCode.LOGIN_FAIL.exp());

        FrameworkUsageException exp = (FrameworkUsageException) session.getAttribute("exception");
        System.out.println(exp);
        return "error";
    }

    /**
     * 测试哨兵的url拦截,sleep随机的时间
     * 
     * @throws InterruptedException
     */
    @RequestMapping("/testSentryUrlRandom")
    public String testSentryUrlRandom() throws InterruptedException {
        Random random = new Random();
        int sleeps = random.nextInt(3000);
        if (sleeps <= 0) {
            sleeps = 500;
        }
        Thread.sleep((long) sleeps);
        return "error";
    }

    /**
     * 测试哨兵的url拦截,sleep固定的时间
     * 
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/testSentryUrlFix")
    public String testSentryUrlFix() throws InterruptedException {
        Thread.sleep(3000);
        return "error";
    }

    /**
     * 测试哨兵的方法拦截,sleep随机的时间
     * 
     * @return
     */
    @RequestMapping("/testSentryMethodRandom")
    public String testSentryMethodRandom() {
        Random random = new Random();
        int sleeps = random.nextInt(3000);
        if (sleeps <= 0) {
            sleeps = 500;
        }
        testService.sleep((long) sleeps);
        return "error";
    }

    /**
     * 测试哨兵的方法拦截,sleep固定的时间
     * 
     * @return
     */
    @RequestMapping("/testSentryMethodFix")
    public String testSentryMethodFix() {
        testService.sleep(4000);
        return "error";
    }

    @RequestMapping("/testDate")
    @ResponseBody
    public Object testDate() {
        Map<String, Object> map = new HashMap<>();
        Date date = new Date();
        map.put("key", date);
        return map;
    }

}

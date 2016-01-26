/**
 * 
 */
package com.github.sunflowerlb.framework.usage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.sunflowerlb.framework.usage.domain.Product;
import com.github.sunflowerlb.framework.usage.service.inner.IUsageService;

/**
 * 
 * @author lb
 */
@Controller
@RequestMapping("/validator")
public class TestValidatorController {

    @Autowired
    IUsageService usageService;

    /**
     * 错误信息以ValidationMessages_zh_CN.properties中的为准的测试
     * http://localhost:8080/ihome-framework-usage/validator/register?email=zxh@163.com&password=abc&phoneNum=
     * @param email
     * @param password
     * @param phoneNum
     */
    @RequestMapping("/register")
    @ResponseBody
    public void register(@RequestParam(required = false) String email, 
    		@RequestParam(required = false) String password, @RequestParam(required = false) Long phoneNum) {
        usageService.register(email, password, phoneNum);
    }
    
    @RequestMapping("/login")
    @ResponseBody
    public void login(@RequestParam(required = false) String userName, @RequestParam(required = false) String password) {
        usageService.login(userName, password);
    }
    
    @RequestMapping("/addProduct1")
    public void addProduct1() {
        Product product = new Product();
        usageService.addProduct(product);
    }
    
    @RequestMapping("/addProduct2")
    @ResponseBody
    public void addProduct2() {
        Product product = new Product();
        usageService.addProduct(product);
    }
    
    @RequestMapping("/sendVerCode1")
    public String sendVerCode1(@RequestParam String phone) {
        usageService.sendVerCode(phone);
        return "error";
    }
    
    @RequestMapping("/sendVerCode2")
    @ResponseBody
    public String sendVerCode2(@RequestParam String phone) {
        usageService.sendVerCode(phone);
        return "error";
    }
}

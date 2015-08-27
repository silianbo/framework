/**
 * 
 */
package com.lb.framework.usage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lb.framework.usage.domain.Product;
import com.lb.framework.usage.service.inner.IUsageService;

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
    public void register(@RequestParam String email, @RequestParam String password, @RequestParam Long phoneNum) {
        usageService.register(email, password, phoneNum);
    }
    
    @RequestMapping("/login")
    @ResponseBody
    public void login(@RequestParam String userName, @RequestParam String password) {
        usageService.login(userName, password);
    }
    
    @RequestMapping("/addProduct")
    public void addProduct() {
        Product product = new Product();
        usageService.addProduct(product);
    }
    
    @RequestMapping("/sendVerCode")
    public String sendVerCode(@RequestParam String phone) {
        usageService.sendVerCode(phone);
        return "error";
    }
}

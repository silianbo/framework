package com.github.sunflowerlb.framework.usage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.sunflowerlb.framework.core.commons.OpResponse;
import com.github.sunflowerlb.framework.usage.exception.ErrCode;
import com.github.sunflowerlb.framework.web.form.FormToken;

@Controller
@RequestMapping("/token")
public class TestTokenController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @RequestMapping("/toLogin")
    @FormToken(generateToken=true)
    public String toLogin() {
        return "testToken";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    @FormToken(checkToken=true)
    public String doLogin(@RequestParam String name, @RequestParam String password, @RequestParam String _ihome_form_token){
        logger.info("doLogin, name:{}, password:{}, token:{}", name, password, _ihome_form_token);
        return "login success";
    }
    
    @RequestMapping("/toLoginJson")
    @FormToken(generateToken=true)
    public OpResponse toLoginJson() {
        OpResponse op = new OpResponse();
        return op;
    }
    
    /**
     * http://localhost:8080/token/doLogin2
     * 先检验token，方法结束的时候生成新的token，适用于那种下一步的操作
     * @return
     */
    @RequestMapping("/doLogin2")
    @FormToken(checkToken=true, generateToken=true)
    @ResponseBody
    public OpResponse doLoginJson(@RequestParam String name, @RequestParam String password) {
        OpResponse op = new OpResponse();
        return op;
    }
    
    
    /**
     * http://localhost:8080/ihome-framework-usage/token/testExp
     * 发生异常的情况下,也发生token
     * @return
     */
    @RequestMapping("/testExp")
    @FormToken(generateToken=true)
    public OpResponse testExp() {
        throw ErrCode.LOGIN_FAIL.exp();
    }
    
    /**
     * http://localhost:8080/ihome-framework-usage/token/testExp
     * 发生异常的情况下,也发生token
     * @return
     */
    @RequestMapping("/testExpJson")
    @FormToken(generateToken=true)
    @ResponseBody
    public OpResponse testExpJson() {
        throw ErrCode.LOGIN_FAIL.exp();
    }

}

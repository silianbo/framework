package com.lb.framework.usage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lb.framework.core.commons.OpResponse;
import com.lb.framework.web.form.FormToken;

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
        OpResponse op = OpResponse.suc();
        return op;
    }

}

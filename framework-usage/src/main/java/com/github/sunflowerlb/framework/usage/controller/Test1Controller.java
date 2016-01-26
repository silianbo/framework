/**
 * 
 */
package com.github.sunflowerlb.framework.usage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sunflowerlb.framework.usage.domain.Product;
import com.github.sunflowerlb.framework.usage.service.inner.TestService;
import com.google.common.collect.Lists;

/**
 * 
 * @author lb
 */
@RestController
@RequestMapping("/test1")
public class Test1Controller {

    @Autowired
    TestService testService;

    /**
     * 测试默认情况下,不配置httpMessageConvert的情况下,会如何返回 我印象中，好像依赖里面加上json的库的话,会默认转成json
     * 
     * @return
     */
    @RequestMapping("/list")
    public List<Product> plist() {
        List<Product> list = Lists.newArrayList();
        list.add(new Product(111, "pone"));
        list.add(new Product(222, "ptwo"));
        return list;
    }
}

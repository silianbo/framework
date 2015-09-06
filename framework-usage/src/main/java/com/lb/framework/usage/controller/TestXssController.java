/**
 * 
 */
package com.lb.framework.usage.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author lb
 */
@Controller
@RequestMapping("/xss")
public class TestXssController {

    private List<String> comments = new CopyOnWriteArrayList<String>();
    
    // 输出的时候在服务端做过滤,只做escapeHtml4的话,JavaScript注入的方式防御不了
    @RequestMapping("/commentsByOutputFilter")
    public String commentsByOutputFilter(HttpServletRequest req, HttpServletResponse resp) {
        List<String> retList = new ArrayList<String>(comments.size());
        for(String comment : comments) {
            String content = comment;
            content = StringEscapeUtils.escapeEcmaScript(content);
            content = StringEscapeUtils.escapeHtml4(content);
            retList.add(content);
        }
        req.setAttribute("commentList", retList);
        return "testXss";
    }

    // 输出的时候不做任何的编码
    @RequestMapping("/comments")
    public String comments(HttpServletRequest req, HttpServletResponse resp) {
        List<String> retList = new ArrayList<String>(comments.size());
        for(String comment : comments) {
            retList.add(comment);
        }
        req.setAttribute("commentList", retList);
        return "testXss";
    }
    
    
    // 输入的时候不做任何编码，输出的时候不做任何编码，完全无防御模式
    @RequestMapping(value="/comment", method=RequestMethod.POST)
    public String addComment(HttpServletRequest req) throws UnsupportedEncodingException {
        String content = req.getParameter("content");
        comments.add(content);
        return "redirect:/xss/comments";
    }
    
    // 输入的时候不做任何编码，在输出的时候编码
    @RequestMapping(value="/commentByOutputFilter", method=RequestMethod.POST)
    public String commentByOutputFilter(HttpServletRequest req) throws UnsupportedEncodingException {
        String content = req.getParameter("content");
        comments.add(content);
        return "redirect:/xss/commentsByOutputFilter";
    }

    // 在输入的时候经过filter编码，输出的时候不编码
    @RequestMapping(value="/commentByInputFilter", method=RequestMethod.POST)
    public String commentByInputFilter(HttpServletRequest req) throws UnsupportedEncodingException {
        String content = req.getParameter("content");
        content = StringEscapeUtils.escapeHtml4(content);
        content = StringEscapeUtils.escapeEcmaScript(content);
        comments.add(content);
        return "redirect:/xss/comments";
    }
    
    @RequestMapping("/cleanComments")
    public String cleanComments() {
        comments.clear();
        return "redirect:/xss/comments";
    }
    
    public static void main(String[] args) {
        String s = "alert('xss')";
        // alert&#x28;&#x27;xss&#x27;&#x29;
        //String str = ESAPI.encoder().encodeForHTML(s);
        //System.out.println(str);

        // alert('xss')
        String str = StringEscapeUtils.escapeHtml4(s);
        System.out.println(str);
    }
}

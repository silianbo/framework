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
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author lb
 */
@Controller
@RequestMapping("/xss")
public class TestXssController {

    private List<String> comments = new CopyOnWriteArrayList<String>();
    
    @RequestMapping("/comments")
    public String commentList(HttpServletRequest req, HttpServletResponse resp) {
        List<String> retList = new ArrayList<String>(comments.size());
        for(String comment : comments) {
            String escapeHtml4 = StringEscapeUtils.escapeHtml4(comment);
            retList.add(escapeHtml4);
        }
        req.setAttribute("commentList", retList);
        return "testXss";
    }
    
    @RequestMapping(value="/comment", method=RequestMethod.POST)
    public String addComment(HttpServletRequest req) throws UnsupportedEncodingException {
        String content = req.getParameter("content");
        comments.add(content);
        return "redirect:/xss/comments";
    }
    
    @RequestMapping("/cleanComments")
    public String cleanComments() {
        comments.clear();
        return "redirect:/xss/comments";
    }
    
    @RequestMapping(value="/commentBySpring")
    public String addCommentSpring(@RequestParam String content) {
        comments.add(content);
        return "redirect:/xss/comments";
    }
}

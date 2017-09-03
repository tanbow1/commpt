package com.tb.commpt.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.tb.commpt.exception.BizLevelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tanbo on 2017/8/22.
 */
@Controller
@RequestMapping("/comm")
public class CommViewController {
    private static Logger logger = LoggerFactory.getLogger(CommViewController.class);

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @RequestMapping("/index")
    public ModelAndView index() {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("data", "hahahhh弹拨");
        ModelAndView mv = new ModelAndView("index");
        // return new ModelAndView("index",modelMap);

        mv.addObject("data2", "谈波");
        return mv;
    }

    @RequestMapping("/error")
    public ModelAndView error() throws BizLevelException {
        throw new BizLevelException("出错了，呵呵呵！");
        //return new ModelAndView("common/error");
    }
}

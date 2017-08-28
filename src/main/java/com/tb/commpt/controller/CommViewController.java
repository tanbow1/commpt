package com.tb.commpt.controller;

import com.tb.commpt.exception.BizLevelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Tanbo on 2017/8/22.
 */
@Controller
@RequestMapping("/commview")
public class CommViewController {
    private static Logger logger = LoggerFactory.getLogger(CommViewController.class);

    @RequestMapping("/index")
    public ModelAndView index(){
        return new ModelAndView("index");
    }

    @RequestMapping("/error")
    public ModelAndView error() throws BizLevelException {
        throw new BizLevelException("出错了，呵呵呵！");
        //return new ModelAndView("common/error");
    }
}

package com.tb.test;

import com.tb.commpt.global.SystemConfig;
import com.tb.commpt.model.DmAccount;
import com.tb.commpt.service.IDmService;
import com.tb.commpt.service.mq.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

/**
 * Created by Tanbo on 2017/8/16.
 */
@Controller
@RequestMapping("/test")
@Scope("prototype")
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private IDmService dmService;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private SystemConfig config;

    @RequestMapping("/hello")
    @ResponseBody
    public String test() {
        logger.info("进入helloController>>>");
        logger.trace("进入helloController>>>");
        logger.debug("进入helloController>>>");
        logger.warn("进入helloController>>>");
        logger.error("进入helloController>>>");
        List<DmAccount> dmAccountList = dmService.selectAllDmAccount();
        //DmAccount dmAccount = dmService.selectDmAccountByPrimaryKey("001");
        System.out.println(dmAccountList);
        System.out.println(config.INTERCEPTOR_IGNORE_URI);
        producerService.sendMessage("amq队列测试。。。");
        return "test controller";
    }

    @RequestMapping("/htmlview")
    public ModelAndView htmlview(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam(value = "langType", defaultValue = "zh", required = false) String langType) {

        if (langType.equals("en")) {
            Locale locale = new Locale("en", "US");
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
        } else {
            Locale locale = new Locale("zh", "CN");
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);

            //(new CookieLocaleResolver()).setLocale (request, response, locale);//cookie国际化
        }

        //从后台代码获取国际化信息
        RequestContext requestContext = new RequestContext(request);
        model.addAttribute("language", requestContext.getMessage("language"));

        return new ModelAndView("index", model);
    }


}

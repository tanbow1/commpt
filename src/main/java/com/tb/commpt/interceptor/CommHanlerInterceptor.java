package com.tb.commpt.interceptor;

import com.tb.commpt.global.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 通用拦截器
 *
 * @author Tanbo
 */
public class CommHanlerInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory
            .getLogger(CommHanlerInterceptor.class);

    @Autowired
    private SystemConfig config;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        System.out.println("========>CommHanlerInterceptor");

        boolean flag = false;
        String url = request.getRequestURL().toString();
        String sessionId = request.getParameter("sessionId");

        String[] IGNORE_URI = config.INTERCEPTOR_IGNORE_URI.split(",");
        logger.info("忽略拦截：" + Arrays.toString(IGNORE_URI));

        for (String s : IGNORE_URI) {
            if (url.contains(s)) {
                flag = true;
                logger.info("忽略该URL:[" + url + "]");
                break;
            }
        }

        if (!flag) {

        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}

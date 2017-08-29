package com.tb.commpt.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SystemStartup implements ApplicationListener {
    private static final Logger logger = LoggerFactory.getLogger(SystemStartup.class);


    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        //加载业务系统配置
//        try {
//            SystemContext.singleton().init();
//            logger.info("加载业务配置完成!");
//        } catch (Exception e) {
//            logger.error("加载业务配置出错", e);
//        }
    }
}

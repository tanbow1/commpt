package com.tb.commpt.global;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by Tanbo on 2017/8/29.
 */
public class AppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //  AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        // context.register(ServiceConfiguration.class);

//        servletContext.addListener(new ContextLoaderListener(context));
//
//        ServletRegistration.Dynamic dispatcher
//                = servletContext.addServlet("dispatcher", new CXFServlet());
//
//        dispatcher.addMapping("/ws-config");

        System.out.println("应用初始化，启动中...");

        try {
            SystemContext.singleton().init();
            System.out.println("加载业务配置完成!");
        } catch (Exception e) {
            System.out.println("加载业务配置出错:" + e);
        }

    }
}

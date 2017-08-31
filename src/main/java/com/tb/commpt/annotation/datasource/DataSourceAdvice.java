package com.tb.commpt.annotation.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * Created by Tanbo on 2017/8/31.
 */
public class DataSourceAdvice implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {

    private Logger logger = LoggerFactory.getLogger(DataSourceAdvice.class);

    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {

    }

    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
// 方法执行之前，设置数据源 
        DataSource d = method.getAnnotation(DataSource.class);
        String methodName = method.getName();
        if (d != null) {
            String datas = d.value();
            if (datas.equals("master")) {
                DataSourceSwitcher.setMaster();
                logger.debug("DataSource advice, anotation method:" + methodName + ", database:master.");
            } else if (datas.equals("slave")) {
                DataSourceSwitcher.setSlave();
                logger.debug("DataSource advice, anotation method:" + methodName + ", database:slave.");
            } else if (datas.equals("secondary.master")) {
                DataSourceSwitcher.setSecondaryMaster();
                logger.debug("DataSource advice, anotation method:" + methodName + ", database:secondary.master.");
            } else if (datas.equals("secondary.slave")) {
                DataSourceSwitcher.setSecondarySlave();
                logger.debug("DataSource advice, anotation method:" + methodName + ", database:secondary.slave.");
            }
        } else if (methodName.startsWith("add")
                || method.getName().startsWith("create")
                || method.getName().startsWith("insert")
                || method.getName().startsWith("save")
                || method.getName().startsWith("edit")
                || method.getName().startsWith("update")
                || method.getName().startsWith("delete")
                || method.getName().startsWith("remove")
                || method.getName().startsWith("replace")) {
            DataSourceSwitcher.setMaster();
            logger.debug("DataSource advice, method:" + methodName + ", database:master.");
        } else {
            DataSourceSwitcher.setSlave();
            logger.debug("DataSource advice, method:" + methodName + ", database:slave.");
        }
    }

    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable {
        DataSourceSwitcher.setSlave();
    }
}

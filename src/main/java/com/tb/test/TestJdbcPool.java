package com.tb.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanbo on 2017/8/22.
 */
public class TestJdbcPool {

    private volatile static BeanFactory factory;

    @Before
    public void init() {
        System.out.println("加载spring配置开始 ............");

        List<String> list = new ArrayList<String>();
        list.add("/mybatis-config/mybatisApplicationContext.xml");   // 将Spring配置文件加入待加载列表

        try {
            factory = new ClassPathXmlApplicationContext(list.toArray(new String[list.size()]));
            // 保证虚拟机退出之前 spring中singtleton对象自定义销毁方法会执行
            ((AbstractApplicationContext) factory).registerShutdownHook();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("加载配置文件时发生错误" + e);
        }

        System.out.println("加载spring配置结束.............");
    }

    @Test
    public void testPool() {
        DataSource ds = (DataSource) factory.getBean("dataSourceDefault");
        Connection con = null;
        try {
            con = ds.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select 1 from dual");
            if (rs.next()) {
                System.out.println("数据库查询结果: " + rs.getInt(1));
            } else {
                System.out.println("数据库查询结果: 未查询到结果");
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println("数据源连接出错");
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
    }
}

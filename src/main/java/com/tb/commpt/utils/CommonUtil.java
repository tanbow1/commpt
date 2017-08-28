package com.tb.commpt.utils;

import com.tb.commpt.constant.ConsCommon;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/***
 * 公共Helper
 *
 * @author Tanbo
 *
 */
public class CommonUtil {

    /**
     * 直接取spring 默认数据源
     *
     * @param request
     * @return
     */
    public static DataSource springDataSource(HttpServletRequest request) {
        DataSource dataSource = null;
        ServletContext context = request.getSession().getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils
                .getRequiredWebApplicationContext(context);
        dataSource = (DataSource) webApplicationContext
                .getBean("dataSourceDefault");
        return dataSource;
    }

    public static DefaultSqlSessionFactory getSqlSessionFactory(
            HttpServletRequest request) {
        DefaultSqlSessionFactory defaultSqlSessionFactory = null;
        ServletContext context = request.getSession().getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils
                .getRequiredWebApplicationContext(context);
        defaultSqlSessionFactory = (DefaultSqlSessionFactory) webApplicationContext
                .getBean("sqlSessionFactory");
        return defaultSqlSessionFactory;
    }

    /**
     * @param request
     * @param autoCommit 批量提交时设置false
     * @return
     */
    public static SqlSession getSqlSession(HttpServletRequest request, boolean autoCommit) {
        return getSqlSessionFactory(request).openSession(
                ExecutorType.BATCH, autoCommit);
    }

    /**
     * java uuid
     */
    public static String getSystemUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * @param pageNumber 页码
     * @param pageSize   每页记录数
     * @return
     */
    public static Map<String, Integer> getPageStartEnd(Integer pageNumber,
                                                       Integer pageSize) {
        Map<String, Integer> pageMap = new ConcurrentHashMap<String, Integer>();
        if (null == pageNumber || pageNumber < 1) {
            pageNumber = ConsCommon.DEFAULT_PAGE_START;
        }
        if (null == pageSize || pageSize < 1) {
            pageSize = ConsCommon.DEFAULT_PAGE_SIZE;
        }
        int pageStart = pageSize * (pageNumber - 1) + 1;
        int pageEnd = pageSize * pageNumber;
        pageMap.put("pageStart", pageStart);
        pageMap.put("pageEnd", pageEnd);
        return pageMap;
    }
}

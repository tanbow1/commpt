package com.tb.commpt.service;

import com.tb.commpt.dao.impl.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.sql.Timestamp;

public abstract class BaseService {

    @Autowired
    protected BaseDao baseDao;


    /**
     * 获取当前数据库时间戳
     *
     * @return
     */
    public Timestamp getNowTimeStamp() throws SQLException {
        return Timestamp.valueOf(getNowTime("YYYY-MM-DD HH24:MI:SS"));
    }

    /**
     * 获取指定格式获取当前数据库时间
     *
     * @param format
     * @return
     */
    public String getNowTime(String format) throws SQLException {
        return baseDao.getDate(format);
    }

    /**
     * 获取当前数据库日期格式为YYYYMMDD
     *
     * @return
     */
    public String getTranDate() throws SQLException {
        return baseDao.getDate("YYYYMMDD");
    }

    /**
     * 获取当前数据库时间格式为HH24MISSFF3
     *
     * @return
     */
    public String getTranTime() throws SQLException {
        return baseDao.getDate("HH24MISSFF3");
    }

    /**
     * 获取当月天数
     */
    public String getDayOfMonth() throws SQLException {
        return baseDao.getDayOfMonth();
    }

    /**
     * 获取当月第一天
     */
    public String getFirstDayOfMonth() throws SQLException {
        return baseDao.getFirstDayOfMonth();
    }

    /**
     * 获取当月第最后一天
     */
    public String getlastDayOfMonth() throws SQLException {
        return baseDao.getlastDayOfMonth();
    }

    /**
     * 获取 n 天后时间
     */
    public String getFewDaysLater(int day) throws SQLException {
        return baseDao.getFewDaysLater(day);
    }

    public String getFewDaysLater(int day, String time) throws SQLException {
        return baseDao.getFewDaysLater(day, time);
    }

    /**
     * 获取 n 小时后时间
     */
    public String getFewhoursLater(int hour) throws SQLException {
        return baseDao.getFewhoursLater(hour);
    }

    public String getFewhoursLater(int hour, String time) throws SQLException {
        return baseDao.getFewhoursLater(hour, time);
    }

    /**
     * 获取 n 分钟后时间
     */
    public String getFewMinsLater(int min) throws SQLException {
        return baseDao.getFewMinsLater(min);
    }

    public String getFewMinsLater(int min, String time) throws SQLException {
        return baseDao.getFewMinsLater(min, time);
    }

    /**
     * 获取 n 秒后时间
     */
    public String getFewSecLater(int sec) throws SQLException {
        return baseDao.getFewSecsLater(sec);
    }

    public String getFewSecLater(int sec, String time) throws SQLException {
        return baseDao.getFewSecsLater(sec, time);
    }

    /**
     * 获取 n 天前时间
     */
    public String getFewDaysAgo(int day) throws SQLException {
        return baseDao.getFewDaysAgo(day);
    }

    public String getFewDaysAgo(int day, String time) throws SQLException {
        return baseDao.getFewDaysAgo(day, time);
    }

    /**
     * 获取 n 小时前时间
     */
    public String getFewhoursAgo(int hour) throws SQLException {
        return baseDao.getFewhoursAgo(hour);
    }

    public String getFewhoursAgo(int hour, String time) throws SQLException {
        return baseDao.getFewhoursAgo(hour, time);
    }

    /**
     * 获取 n 分钟前时间
     */
    public String getFewMinsAgo(int min) throws SQLException {
        return baseDao.getFewMinsAgo(min);
    }

    public String getFewMinsAgo(int min, String time) throws SQLException {
        return baseDao.getFewMinsAgo(min, time);
    }

    /**
     * 获取 n 秒前时间
     */
    public String getFewSecsAgo(int sec) throws SQLException {
        return baseDao.getFewSecsAgo(sec);
    }

    public String getFewSecsAgo(int sec, String time) throws SQLException {
        return baseDao.getFewSecsAgo(sec, time);
    }

}

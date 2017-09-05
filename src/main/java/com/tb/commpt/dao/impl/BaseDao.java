package com.tb.commpt.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;


@Repository
public class BaseDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 当前时间+8小时
     *
     * @return
     */
    public String getNowLocal() {
        return jdbcTemplate.queryForObject("SELECT TO_CHAR(SYSDATE +INTERVAL '8' HOUR,'YYYY-MM-DD HH24:MI:SS') FROM DUAL", new Object[]{},
                String.class);
    }

    public String getDate(String dateType) {
        if (null == dateType || "".equals(dateType)) {
            dateType = "YYYY-MM-DD";
        }
        return jdbcTemplate.queryForObject("SELECT TO_CHAR(SYSDATE,?) FROM DUAL", new Object[]{dateType},
                String.class);
    }

    /**
     * 获取当月天数
     */
    public String getDayOfMonth() {
        return jdbcTemplate.queryForObject("SELECT LAST_DAY(SYSDATE)-LAST_DAY(ADD_MONTHS(SYSDATE,-1)) FROM DUAL",
                new Object[]{}, String.class);
    }

    /**
     * 获取当月第一天
     */
    public String getFirstDayOfMonth() {
        return jdbcTemplate.queryForObject("SELECT TO_CHAR(TRUNC(SYSDATE, 'MM'),'YYYY-MM-DD') FROM DUAL", new Object[]{}, String.class);
    }

    /**
     * 获取当月第最后一天
     */
    public String getlastDayOfMonth() {
        return jdbcTemplate.queryForObject("SELECT TO_CHAR(TRUNC(LAST_DAY(SYSDATE)),'YYYY-MM-DD') FROM dual", new Object[]{}, String.class);
    }

    /**
     * 获取 n 天后时间
     */
    public String getFewDaysLater(int day) {
        return jdbcTemplate.queryForObject("SELECT TO_CHAR(SYSDATE + ?,'YYYY-MM-DD HH24:MI:SS') FROM dual", new Object[]{day}, String.class);
    }

    public String getFewDaysLater(int day, String time) {
        return jdbcTemplate.queryForObject("SELECT TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') + ? FROM dual", new Object[]{time, day}, String.class);
    }

    /**
     * 获取 n 小时后时间
     */
    public String getFewhoursLater(int hour) {
        return jdbcTemplate.queryForObject("SELECT TO_CHAR(SYSDATE + ?/24,'YYYY-MM-DD HH24:MI:SS') FROM dual", new Object[]{hour}, String.class);
    }

    public String getFewhoursLater(int hour, String time) {
        return jdbcTemplate.queryForObject("SELECT TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') + ?/24 FROM dual", new Object[]{time, hour}, String.class);
    }

    /**
     * 获取 n 分钟后时间
     */
    public String getFewMinsLater(int min) {
        return jdbcTemplate.queryForObject("SELECT TO_CHAR(SYSDATE + ?/1440,'YYYY-MM-DD HH24:MI:SS') FROM dual", new Object[]{min}, String.class);
    }

    public String getFewMinsLater(int min, String time) {
        return jdbcTemplate.queryForObject("SELECT TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') + ?/1440 FROM dual", new Object[]{time, min}, String.class);
    }

    /**
     * 获取 n 秒后时间
     */
    public String getFewSecsLater(int sec) {
        return jdbcTemplate.queryForObject("SELECT TO_CHAR(SYSDATE + ?/84600,'YYYY-MM-DD HH24:MI:SS') FROM dual", new Object[]{sec}, String.class);
    }

    public String getFewSecsLater(int sec, String time) {
        return jdbcTemplate.queryForObject("SELECT TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') + ?/84600 FROM dual", new Object[]{time, sec}, String.class);
    }

    /**
     * 获取 n 天前时间
     */
    public String getFewDaysAgo(int day) {
        return jdbcTemplate.queryForObject("SELECT TO_CHAR(SYSDATE - ?,'YYYY-MM-DD HH24:MI:SS') FROM dual", new Object[]{day}, String.class);
    }

    public String getFewDaysAgo(int day, String time) {
        return jdbcTemplate.queryForObject("SELECT TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') - ? FROM dual", new Object[]{time, day}, String.class);
    }

    /**
     * 获取 n 小时前时间
     */
    public String getFewhoursAgo(int hour) {
        return jdbcTemplate.queryForObject("SELECT TO_CHAR(SYSDATE - ?/24,'YYYY-MM-DD HH24:MI:SS') FROM dual", new Object[]{hour}, String.class);
    }

    public String getFewhoursAgo(int hour, String time) {
        return jdbcTemplate.queryForObject("SELECT TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') - ?/24 FROM dual", new Object[]{time, hour}, String.class);
    }

    /**
     * 获取 n 分钟前时间
     */
    public String getFewMinsAgo(int min) {
        return jdbcTemplate.queryForObject("SELECT TO_CHAR(SYSDATE - ?/1440,'YYYY-MM-DD HH24:MI:SS') FROM dual", new Object[]{min}, String.class);
    }

    public String getFewMinsAgo(int min, String time) {
        return jdbcTemplate.queryForObject("SELECT TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') - ?/1440 FROM dual", new Object[]{time, min}, String.class);
    }

    /**
     * 获取 n 秒前时间
     */
    public String getFewSecsAgo(int sec) {
        return jdbcTemplate.queryForObject("SELECT TO_CHAR(SYSDATE - ?/84600,'YYYY-MM-DD HH24:MI:SS') FROM dual", new Object[]{sec}, String.class);
    }

    public String getFewSecsAgo(int sec, String time) {
        return jdbcTemplate.queryForObject("SELECT TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') - ?/84600 FROM dual", new Object[]{time, sec}, String.class);
    }

}

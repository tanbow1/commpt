package com.tb.commpt.utils;

import org.apache.poi.ss.usermodel.DateUtil;

import java.util.Calendar;

/**
 * Created by Tanbo on 2017/9/20.
 * <p>
 * 自定义xssf日期工具类
 */
public class XSSFDateUtil extends DateUtil {

    protected static int absoluteDay(Calendar cal, boolean use1904windowing) {
        return DateUtil.absoluteDay(cal, use1904windowing);
    }

}

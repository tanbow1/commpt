package com.tb.commpt.constant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Tanbo on 2017/9/21.
 */
public class ContentType {

    private static Map<String, String> contentTypeMap = new ConcurrentHashMap<String, String>();

    static {
        contentTypeMap.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        contentTypeMap.put("xls", "application/x-xls");
    }

    public static String getContentType(String key) {
        if (contentTypeMap.containsKey(key)) {
            return contentTypeMap.get(key);
        }
        return null;
    }


}

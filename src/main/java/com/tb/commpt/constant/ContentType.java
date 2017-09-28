package com.tb.commpt.constant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Tanbo on 2017/9/21.
 */
public class ContentType {

    private static Map<String, String> contentTypeMap = new ConcurrentHashMap<String, String>();

    static {
        contentTypeMap.put("json", "application/json");
        contentTypeMap.put("text", "text/plain");
        contentTypeMap.put("html", "text/html");
        contentTypeMap.put("xml", "text/xml");
        contentTypeMap.put("jpg", "image/jpeg");
        contentTypeMap.put("jpeg", "image/jpeg");
        contentTypeMap.put("png", "image/png");
        contentTypeMap.put("tif", "image/tiff");
        contentTypeMap.put("tiff", "image/tiff");
        contentTypeMap.put("js", "application/x-javascript");
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

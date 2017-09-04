package com.tb.commpt.constant;

public class ConsCommon {

    public static final String SYS_PREFIX = "COMMONPT_";

    public static final String UTF8 = "UTF-8";

    public static final String GBK = "GBK";

    // //////////////////////////////////////////////////////////


    public static final int BATCH_COUNT = 100;//批量提交数

    // //////////////////////////////////////////////////////////

    public static final String ROLE_ID_SUPER = "001";// 超级用户roleId

    // //////////////////////////////////////////////////////////

    public static final int DEFAULT_PAGE_START = 1;//起始页

    public static final int DEFAULT_PAGE_SIZE = 10;// 默认单页条数


    // //////////////////////////////////////////////////////////

    public static final String SUCCESS_CODE = "1";

    public static final String SUCCESS_MSG = "处理成功";

    public static final String ERROR_CODE = "0";

    public static final String ERROR_MSG = "处理失败";

    public static final String ERROR_CODE_UNKNOW = "-1";

    public static final String ERROR_MSG_UNKNOW = "未知错误";

    // //////////////////////////////////////////////////////////

    public static final String JWT_ID = "jwt";

    public static final int JWT_TTL = 60 * 60 * 1000;  //millisecond

    public static final int JWT_REFRESH_INTERVAL = 55 * 60 * 1000;  //millisecond

    public static final int JWT_REFRESH_TTL = 24 * 60 * 60 * 1000;  //millisecond

    public static final String ACCESS_TOKEN = SYS_PREFIX + "ACCESS_TOKEN";

    public static final String REFRESH_TOKEN = SYS_PREFIX + "REFRESH_TOKEN";


}

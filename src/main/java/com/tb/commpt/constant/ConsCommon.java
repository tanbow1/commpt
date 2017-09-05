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

    public static final String WARN_CODE_001 = "W0001";
    public static final String WARN_MSG_001 = "账户已存在";

    public static final String WARN_CODE_002 = "W0002";
    public static final String WARN_MSG_002 = "该证件号码已经被注册";

    public static final String WARN_CODE_003 = "W0003";
    public static final String WARN_MSG_003 = "该手机号已经被注册";

    public static final String WARN_CODE_004 = "W0004";
    public static final String WARN_MSG_004 = "参数accessToken为空";

    public static final String WARN_CODE_005 = "W0005";
    public static final String WARN_MSG_005 = "参数refreshToken为空";

    public static final String WARN_CODE_006 = "W0006";
    public static final String WARN_MSG_006 = "token失效";

    public static final String WARN_CODE_007 = "W0007";
    public static final String WARN_MSG_007 = "用户名或密码有误";

    public static final String WARN_CODE_008 = "W0008";
    public static final String WARN_MSG_008 = "";

    public static final String WARN_CODE_009 = "W0009";
    public static final String WARN_MSG_009 = "";

    public static final String WARN_CODE_010 = "W0010";
    public static final String WARN_MSG_010 = "";

    public static final String WARN_CODE_011 = "W0011";
    public static final String WARN_MSG_011 = "";

    public static final String WARN_CODE_012 = "W0012";
    public static final String WARN_MSG_012 = "";

    public static final String WARN_CODE_013 = "W0013";
    public static final String WARN_MSG_013 = "";

    public static final String WARN_CODE_014 = "W0014";
    public static final String WARN_MSG_014 = "";

    public static final String WARN_CODE_015 = "W0015";
    public static final String WARN_MSG_015 = "";

    public static final String WARN_CODE_016 = "W0016";
    public static final String WARN_MSG_016 = "";

    public static final String WARN_CODE_017 = "W0017";
    public static final String WARN_MSG_017 = "";

    public static final String WARN_CODE_018 = "W0018";
    public static final String WARN_MSG_018 = "";

    public static final String WARN_CODE_019 = "W0019";
    public static final String WARN_MSG_019 = "";

    public static final String WARN_CODE_020 = "W0020";
    public static final String WARN_MSG_020 = "";

    // //////////////////////////////////////////////////////////

    public static final String JWT_ID = "jwt";

    public static final int JWT_TTL = 60 * 60 * 1000;  //millisecond

    public static final int JWT_REFRESH_INTERVAL = 55 * 60 * 1000;  //millisecond

    public static final int JWT_REFRESH_TTL = 24 * 60 * 60 * 1000;  //millisecond

    public static final String ACCESS_TOKEN = SYS_PREFIX + "ACCESS_TOKEN";

    public static final String REFRESH_TOKEN = SYS_PREFIX + "REFRESH_TOKEN";


}

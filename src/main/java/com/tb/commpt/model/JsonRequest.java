package com.tb.commpt.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Tanbo on 2017/8/28.
 */
public class JsonRequest extends BaseModel {

    //token
    private String accessToken;

    private String refreshToken;

    //请求具体参数
    private Map<String, Object> reqData = new ConcurrentHashMap<String, Object>();

    //服务名
    private String serviceName;

    //方法名
    private String methodName;


    //方法中参数（索引index 类型type 值value）,用于确定同名方法不同参数,index从1开始
    private List<Map<String, Object>> methodParams = new ArrayList<Map<String, Object>>();

    public List<Map<String, Object>> getMethodParams() {
        return methodParams;
    }

    public void setMethodParams(List<Map<String, Object>> methodParams) {
        this.methodParams = methodParams;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Map<String, Object> getReqData() {
        return reqData;
    }

    public void setReqData(Map<String, Object> reqData) {
        this.reqData = reqData;
    }


}

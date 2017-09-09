package com.tb.commpt.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Tanbo on 2017/8/28.
 */
public class JsonRequest extends BaseModel {
    private Map<String, Object> reqData = new ConcurrentHashMap<String, Object>();

    private String serviceName;

    private String methodName;

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

    private String accessToken;

    private String refreshToken;

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

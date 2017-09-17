package com.tb.commpt.model.comm;

import com.tb.commpt.constant.ConsCommon;
import com.tb.commpt.model.comm.BaseModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JsonResponse extends BaseModel {
    public JsonResponse() {

    }

    public JsonResponse(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }

    public JsonResponse(String msg, String code, ConcurrentHashMap<String, Object> repData) {
        this.msg = msg;
        this.code = code;
        this.repData = repData;
    }

    private String msg = ConsCommon.SUCCESS_MSG;
    private String detailMsg;
    private String code = ConsCommon.SUCCESS_CODE;
    private ConcurrentHashMap<String, Object> repData = new ConcurrentHashMap<String, Object>();

    public String getDetailMsg() {
        return detailMsg;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public Map<String, Object> getRepData() {
        return repData;
    }

    public void setRepData(ConcurrentHashMap<String, Object> repData) {
        this.repData = repData;
    }
}

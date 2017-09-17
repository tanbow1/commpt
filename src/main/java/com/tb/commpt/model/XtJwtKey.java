package com.tb.commpt.model;

import com.tb.commpt.model.comm.BaseModel;

public class XtJwtKey extends BaseModel {
    private String userId;

    private String accessToken;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }
}
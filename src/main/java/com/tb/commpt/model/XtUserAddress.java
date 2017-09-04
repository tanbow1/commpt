package com.tb.commpt.model;

import java.util.Date;

public class XtUserAddress extends XtUserAddressKey {
    private String isDefault;

    private String yxbj;

    private Date lrSj;

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault == null ? null : isDefault.trim();
    }

    public String getYxbj() {
        return yxbj;
    }

    public void setYxbj(String yxbj) {
        this.yxbj = yxbj == null ? null : yxbj.trim();
    }

    public Date getLrSj() {
        return lrSj;
    }

    public void setLrSj(Date lrSj) {
        this.lrSj = lrSj;
    }
}
package com.tb.commpt.model;

import java.util.Date;

public class XtUserAccount extends XtUserAccountKey {
    private String isDefault;

    private String yxbj;

    private Date lrSj;

    private String pass;

    private String passEnc;

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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass == null ? null : pass.trim();
    }

    public String getPassEnc() {
        return passEnc;
    }

    public void setPassEnc(String passEnc) {
        this.passEnc = passEnc == null ? null : passEnc.trim();
    }
}
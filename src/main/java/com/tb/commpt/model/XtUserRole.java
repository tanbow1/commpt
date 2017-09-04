package com.tb.commpt.model;

public class XtUserRole extends XtUserRoleKey {
    private String yxbj;

    public String getYxbj() {
        return yxbj;
    }

    public void setYxbj(String yxbj) {
        this.yxbj = yxbj == null ? null : yxbj.trim();
    }
}
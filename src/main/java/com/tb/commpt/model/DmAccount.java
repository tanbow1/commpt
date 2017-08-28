package com.tb.commpt.model;

public class DmAccount extends BaseModel {
    private String accountType;

    private String accountDes;

    private String yxbj;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
    }

    public String getAccountDes() {
        return accountDes;
    }

    public void setAccountDes(String accountDes) {
        this.accountDes = accountDes == null ? null : accountDes.trim();
    }

    public String getYxbj() {
        return yxbj;
    }

    public void setYxbj(String yxbj) {
        this.yxbj = yxbj == null ? null : yxbj.trim();
    }
}
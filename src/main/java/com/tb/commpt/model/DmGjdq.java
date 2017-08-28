package com.tb.commpt.model;

public class DmGjdq extends BaseModel {
    private String gjdqMcZ;

    private String gjdqMcE;

    private String gjdqMcdm;

    private String gjdqDhdm;

    private String yxbj;

    private String gjdqId;

    public String getGjdqMcZ() {
        return gjdqMcZ;
    }

    public void setGjdqMcZ(String gjdqMcZ) {
        this.gjdqMcZ = gjdqMcZ == null ? null : gjdqMcZ.trim();
    }

    public String getGjdqMcE() {
        return gjdqMcE;
    }

    public void setGjdqMcE(String gjdqMcE) {
        this.gjdqMcE = gjdqMcE == null ? null : gjdqMcE.trim();
    }

    public String getGjdqMcdm() {
        return gjdqMcdm;
    }

    public void setGjdqMcdm(String gjdqMcdm) {
        this.gjdqMcdm = gjdqMcdm == null ? null : gjdqMcdm.trim();
    }

    public String getGjdqDhdm() {
        return gjdqDhdm;
    }

    public void setGjdqDhdm(String gjdqDhdm) {
        this.gjdqDhdm = gjdqDhdm == null ? null : gjdqDhdm.trim();
    }

    public String getYxbj() {
        return yxbj;
    }

    public void setYxbj(String yxbj) {
        this.yxbj = yxbj == null ? null : yxbj.trim();
    }

    public String getGjdqId() {
        return gjdqId;
    }

    public void setGjdqId(String gjdqId) {
        this.gjdqId = gjdqId == null ? null : gjdqId.trim();
    }
}
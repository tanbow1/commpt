package com.tb.commpt.model;

import com.tb.commpt.model.comm.BaseModel;

public class DmProductType extends BaseModel {
    private String typeId;

    private String typeName;

    private String yxbj;

    private String pId;

    private String typeDesc;

    private Short px;

    private String _parentId;

    public String get_parentId() {
        return _parentId;
    }

    public void set_parentId(String _parentId) {
        if (null == _parentId) {
            this._parentId = pId;
        } else {
            this._parentId = _parentId;
        }
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getYxbj() {
        return yxbj;
    }

    public void setYxbj(String yxbj) {
        this.yxbj = yxbj == null ? null : yxbj.trim();
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId == null ? null : pId.trim();
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc == null ? null : typeDesc.trim();
    }

    public Short getPx() {
        return px;
    }

    public void setPx(Short px) {
        this.px = px;
    }
}
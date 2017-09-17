package com.tb.commpt.model;

import com.tb.commpt.model.comm.BaseModel;

public class DmMenu extends BaseModel {
    private String menuId;

    private String parentId;

    private String menuName;

    private String url;

    private String yxbj;

    private String openType;

    private String state;

    private String haschildren;

    private String readonly;

    public String getReadonly() {
        return readonly;
    }

    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHaschildren() {
        return haschildren;
    }

    public void setHaschildren(String haschildren) {
        this.haschildren = haschildren;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getYxbj() {
        return yxbj;
    }

    public void setYxbj(String yxbj) {
        this.yxbj = yxbj == null ? null : yxbj.trim();
    }

    public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType == null ? null : openType.trim();
    }
}
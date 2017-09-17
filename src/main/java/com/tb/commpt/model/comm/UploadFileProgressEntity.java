package com.tb.commpt.model.comm;

/**
 * Created by Tanbo on 2017/9/16.
 * <p>
 * 文件上传进度
 */
public class UploadFileProgressEntity extends BaseModel {
    private long pBytesRead = 0L;//已读的byte总数
    private long pContentLength = 0L;//要读的文件总byte数,如果长度未知，值为-1
    private int pItems = 0;//当前所读文件的索引(从1开始)，单文件上传时始终为1,0表示没有文件在读

    public long getpBytesRead() {
        return pBytesRead;
    }

    public void setpBytesRead(long pBytesRead) {
        this.pBytesRead = pBytesRead;
    }

    public long getpContentLength() {
        return pContentLength;
    }

    public void setpContentLength(long pContentLength) {
        this.pContentLength = pContentLength;
    }

    public int getpItems() {
        return pItems;
    }

    public void setpItems(int pItems) {
        this.pItems = pItems;
    }
}

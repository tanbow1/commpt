package com.tb.commpt.listener;

import com.tb.commpt.model.comm.UploadFileProgressEntity;
import org.apache.commons.fileupload.ProgressListener;

import javax.servlet.http.HttpSession;

/**
 * Created by Tanbo on 2017/9/16.
 * <p>
 * 文件上传 进度监听，将文件上传的进度信息保存在session中
 */
public class UploadFileProgressListener implements ProgressListener {

    private HttpSession session;

    public UploadFileProgressListener(HttpSession session) {
        this.session = session;
        UploadFileProgressEntity uploadFileProgressEntity = new UploadFileProgressEntity();
        session.setAttribute("uploadFileProgressEntity", uploadFileProgressEntity);
    }

    @Override
    public void update(long pBytesRead, long pContentLength, int pItems) {
        UploadFileProgressEntity uploadFileProgressEntity = (UploadFileProgressEntity) session.getAttribute("uploadFileProgressEntity");
        uploadFileProgressEntity.setpBytesRead(pBytesRead);
        uploadFileProgressEntity.setpContentLength(pContentLength);
        uploadFileProgressEntity.setpItems(pItems);
        session.setAttribute("uploadFileProgressEntity", uploadFileProgressEntity);
    }
}

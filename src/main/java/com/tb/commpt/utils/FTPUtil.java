package com.tb.commpt.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Tanbo on 2017/9/17.
 */
public class FTPUtil {

    private static Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    public FTPUtil() {
    }


    public static boolean uploadFile(String url, String username, String password, String path, String filename, InputStream input, String encoding) throws Exception {
        boolean success = false;
        boolean wjj_flag = false;
        FTPClient ftp = new FTPClient();

        try {
            logger.info("=============1）创建FTP客户端成功，开始连接FTP：[" + url + "]");
            ftp.connect(url);
            logger.info("=============2）FTP已连接");
            ftp.setControlEncoding(encoding == null ? "utf-8" : encoding);
            FTPClientConfig conf = new FTPClientConfig("WINDOWS");
            conf.setServerLanguageCode("zh");
            ftp.login(username, password);
            ftp.enterLocalPassiveMode();
            logger.info("=============3）开始登录FTP");
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                logger.info("=============4）连接ftp服务器失败===============");
                throw new Exception("连接ftp服务器失败!");
            }

            logger.info("=============4）登陆ftp服务器成功===============");
            wjj_flag = ftp.changeWorkingDirectory(path);
            if (!wjj_flag && !creatDir(ftp, path)) {
                throw new Exception("目录" + path + "创建失败!");
            }

            logger.info("=============5）开始上传文件");
            ftp.setFileType(2);
            ftp.storeFile(filename, input);
            input.close();
            ftp.logout();
            success = true;
            logger.info("=============6）ftp文件上传成功===============");
        } catch (IOException var19) {
            throw new Exception(var19.getMessage());
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException var18) {
                    throw new Exception(var18.getMessage());
                }
            }

        }

        return success;
    }

    public static boolean deleteFile(String url, String username, String password, String path, String filename) throws Exception {
        boolean success = false;
        boolean wjj_flag = false;
        FTPClient ftp = new FTPClient();

        try {
            ftp.connect(url);
            ftp.setControlEncoding("GBK");
            FTPClientConfig conf = new FTPClientConfig("WINDOWS");
            conf.setServerLanguageCode("zh");
            ftp.login(username, password);
            ftp.enterLocalPassiveMode();
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                logger.info("===============连接ftp服务器失败===============");
                throw new Exception("连接ftp服务器失败!");
            }

            logger.info("===============登陆ftp服务器成功===============");
            String filename2 = new String(filename.getBytes("GBK"), "ISO-8859-1");
            wjj_flag = ftp.changeWorkingDirectory(path);
            if (!wjj_flag && !creatDir(ftp, path)) {
                throw new Exception("目录" + path + "创建失败!");
            }

            ftp.deleteFile(filename2);
            ftp.logout();
            success = true;
            logger.info("===============ftp服务器删除文件成功===============");
        } catch (IOException var18) {
            logger.info(var18.getMessage());
            throw new Exception(var18.getMessage());
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException var17) {
                    logger.info(var17.getMessage());
                    throw new Exception(var17.getMessage());
                }
            }

        }

        return success;
    }

    public static boolean isDirExist(String fileName, FTPFile[] fs) {
        for (int i = 0; i < fs.length; ++i) {
            FTPFile ff = fs[i];
            if (ff.getName().equals(fileName)) {
                return true;
            }
        }

        return false;
    }

    public static String changeName(String filename, FTPFile[] fs) {
        int n = 0;
        StringBuffer filename1 = new StringBuffer("");

        String a;
        StringBuffer name;
        StringBuffer suffix;
        for (filename1 = filename1.append(filename); isDirExist(filename1.toString(), fs); filename1 = name.append(a).append(".").append(suffix)) {
            ++n;
            a = "[" + n + "]";
            int b = filename1.lastIndexOf(".");
            int c = filename1.lastIndexOf("[");
            if (c < 0) {
                c = b;
            }

            name = new StringBuffer(filename1.substring(0, c));
            suffix = new StringBuffer(filename1.substring(b + 1));
        }

        return filename1.toString();
    }

    public static boolean creatDir(FTPClient ftp, String path) throws Exception {
        boolean flag = false;
        String[] ml = path.split("/");

        for (int i = 0; i < ml.length; ++i) {
            ftp.makeDirectory(ml[i]);
            flag = ftp.changeWorkingDirectory(ml[i]);
            if (!flag) {
                return false;
            }
        }

        return flag;
    }

    public static boolean uploadBase64File(String url, String username, String password, String path, String filename, String base64str) throws Exception {
        if (base64str != null) {
            base64str = base64str.replace("data:image/jpeg;base64,", "");
            base64str = base64str.replace("data:image/png;base64,", "");
            base64str = base64str.replace("data:image/gif;base64,", "");
            base64str = base64str.replace("data:image/bmp;base64,", "");
            base64str = base64str.replace("data:application/pdf;base64,", "");
            byte[] b = Base64.decodeBase64(base64str);

            for (int j = 0; j < b.length; ++j) {
                if (b[j] < 0) {
                    b[j] = (byte) (b[j] + 256);
                }
            }

            InputStream inputStream = new ByteArrayInputStream(b);
            return uploadFile(url, username, password, path, filename, inputStream, "utf-8");
        } else {
            return false;
        }
    }
}

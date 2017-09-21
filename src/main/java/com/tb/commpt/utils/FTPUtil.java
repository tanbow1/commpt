package com.tb.commpt.utils;

import com.tb.commpt.constant.ConsCommon;
import com.tb.commpt.exception.SystemLevelException;
import com.tb.commpt.global.SystemContext;
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


    public static FTPClient getFtpClient(String host, String username, String password, String encoding, String systemKey, String languageCode) throws SystemLevelException {
        FTPClient ftpClient = new FTPClient();
        logger.info("1）创建FTP客户端成功，开始连接FTP[" + host + "]");
        try {
            ftpClient.connect(host);
            logger.info("2）FTP已连接");
            ftpClient.setControlEncoding(null == encoding ? ConsCommon.UTF8 : encoding);
            FTPClientConfig ftpClientConfig = new FTPClientConfig(null == systemKey ? FTPClientConfig.SYST_UNIX : systemKey);
            ftpClientConfig.setServerLanguageCode(null == languageCode ? ConsCommon.LANGUAGE_ZH : languageCode);
            logger.info("3）FTP相关配置成功");
            ftpClient.login(username, password);
            ftpClient.enterLocalActiveMode();//主动模式
            logger.info("4）开始登录FTP");
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new SystemLevelException("连接FTP服务器失败");
            }
            logger.info("5）已登录FTP");
        } catch (IOException ex) {
            throw new SystemLevelException("连接FTP服务器失败");
        }

        return ftpClient;
    }


    public static void closeFtpClient(FTPClient ftpClient) throws SystemLevelException {
        if (null != ftpClient && ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException ex) {
                throw new SystemLevelException(ex.getMessage());
            }
        }
    }


    /**
     * 文件上传 默认读取配置文件
     *
     * @param filename
     * @param input
     * @return
     */
    public static boolean uploadFile(String filename, InputStream input) throws IOException, SystemLevelException {
        FTPClient ftpClient = getFtpClient(SystemContext.singleton().getValueAsString("ftp.host"),
                SystemContext.singleton().getValueAsString("ftp.username"),
                SystemContext.singleton().getValueAsString("ftp.password"),
                null, null, null);
        String basePath = SystemContext.singleton().getValueAsString("ftp.uploadBasepath");

        if (!ftpClient.changeWorkingDirectory(basePath) && !creatDir(ftpClient, basePath)) {
            throw new SystemLevelException("上传目录[" + basePath + "]创建失败!");
        }
        logger.info("1）开始上传文件");
        ftpClient.setFileType(2);
        ftpClient.storeFile(filename, input);
        input.close();
        logger.info("2）FTP文件上传成功");
        ftpClient.logout();
        closeFtpClient(ftpClient);
        logger.info("3）FTP资源回收");
        return true;
    }

    /**
     * 文件上传 参数自行传入
     *
     * @param host
     * @param username
     * @param password
     * @param path
     * @param filename
     * @param input
     * @param encoding
     * @return
     * @throws SystemLevelException
     * @throws IOException
     */
    public static boolean uploadFile(String host, String username, String password, String path, String filename, InputStream input, String encoding, String systemKey, String languageCode) throws SystemLevelException, IOException {
        FTPClient ftp = getFtpClient(host, username, password, encoding, systemKey, languageCode);
        if (!ftp.changeWorkingDirectory(path) && !creatDir(ftp, path)) {
            throw new SystemLevelException("上传目录[" + path + "]创建失败!");
        }

        logger.info("1）开始上传文件");
        ftp.setFileType(2);
        ftp.storeFile(filename, input);
        input.close();
        logger.info("2）FTP文件上传成功");
        ftp.logout();
        closeFtpClient(ftp);
        logger.info("3）FTP资源回收");
        return true;
    }

    public static boolean deleteFile(String host, String username, String password, String path, String filename, String encoding) throws Exception {
        FTPClient ftp = getFtpClient(host, username, password, encoding, null, null);

        String _filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
        if (!ftp.changeWorkingDirectory(path)) {
            throw new SystemLevelException("目录[" + path + "]切换失败!");
        }

        ftp.deleteFile(_filename);
        logger.info("1）FTP服务器删除文件成功");
        ftp.logout();
        closeFtpClient(ftp);
        logger.info("2）FTP资源回收");
        return true;
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

    public static boolean creatDir(FTPClient ftp, String path) throws IOException {
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

    /**
     * 页面 base64字符串传入
     *
     * @param host
     * @param username
     * @param password
     * @param path
     * @param filename
     * @param base64str
     * @return
     * @throws Exception
     */
    public static boolean uploadBase64File(String host, String username, String password, String path, String filename, String base64str) throws Exception {
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

            return uploadFile(host, username, password, path, filename, inputStream, "utf-8", null, null);
        } else {
            return false;
        }
    }
}

package com.tb.commpt.utils;


import com.tb.commpt.constant.ConsCommon;
import com.tb.commpt.exception.SystemLevelException;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileTool {
    /**
     * byte转MB或KB
     *
     * @param bytes
     * @return
     */
    public static String bytes2kb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        if (returnValue > 1)
            return (returnValue + "MB");
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        return (returnValue + "KB");
    }

    public static long getFileSize(File file) throws SystemLevelException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return fis.available();
        } catch (Exception e) {
            throw new SystemLevelException(e.getMessage());
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new SystemLevelException(e.getMessage());
                }
            }
        }
    }

    public static final InputStream byte2Input(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }

    public static final byte[] input2byte(InputStream inputStream)
            throws SystemLevelException {
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            byte[] in2b = swapStream.toByteArray();
            return in2b;
        } catch (Exception e) {
            throw new SystemLevelException(e.getMessage());
        }
    }

    public static byte[] getFileByte(String fileName)
            throws SystemLevelException {
        try {
            InputStream inputStream = new FileInputStream(new File(fileName));
            return FileTool.input2byte(inputStream);
        } catch (Exception e) {
            throw new SystemLevelException(e.getMessage());
        }

    }

    /**
     * 递归获取某目录下的所有文件
     *
     * @param obj 传入文件目录
     * @return
     */
    public static List<File> getListFiles(Object obj) {
        File directory = null;
        if (obj instanceof File) {
            directory = (File) obj;
        } else {
            directory = new File(obj.toString());
        }
        List<File> files = new ArrayList<File>();
        if (directory.isFile()) {
            files.add(directory);
            return files;
        } else if (directory.isDirectory()) {
            File[] fileArr = directory.listFiles();
            for (int i = 0; i < fileArr.length; i++) {
                File fileOne = fileArr[i];
                files.addAll(getListFiles(fileOne));
            }
        }
        return files;
    }


    public static String clobToString(Clob clob) throws SystemLevelException {
        if (null == clob) {
            return "";
        }
        StringBuffer strBuf = new StringBuffer();
        try {
            Reader is = clob.getCharacterStream();
            BufferedReader br = new BufferedReader(is);


            String s = br.readLine();
            while (s != null) {
                strBuf.append(s);
                s = br.readLine();
            }
        } catch (IOException e) {
            throw new SystemLevelException(e.getMessage());
        } catch (SQLException e) {
            throw new SystemLevelException(e.getMessage());
        }
        return strBuf.toString();
    }

    public static char[] readCLOB(ResultSet rs, String col)
            throws SystemLevelException {
        BufferedWriter out = null;
        char[] data;
        BufferedReader in = null;
        CharArrayWriter writer = null;
        try {
            if (rs.getClob(col) == null) {
                return null;
            }
            writer = new CharArrayWriter();
            out = new BufferedWriter(writer);
            Clob clob = rs.getClob(col);
            in = new BufferedReader(clob.getCharacterStream());
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
            out.flush();
            data = writer.toCharArray();
        } catch (Exception e) {
            throw new SystemLevelException();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (writer != null)
                    writer.close();
            } catch (Exception e) {
                throw new SystemLevelException();
            }
        }
        return data;
    }

    public static String getTmepFile(String fielType) {
        return System.getProperty("user.dir") + File.separator + "tmp"
                + File.separator + UUID.randomUUID().toString() + fielType;
    }

    public static String getTemplateFile(String fileName) {
        return System.getProperty("user.dir") + File.separator + "template"
                + File.separator + fileName;
    }

    // 读取指定路径文本文件
    public static String readText(String filePath) {
        StringBuilder str = new StringBuilder();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(FileTool.class.getResource(filePath).getPath()), ConsCommon.UTF8));
            String s;
            try {
                while ((s = bufferedReader.readLine()) != null)
                    str.append(s + '\n');
            } finally {
                bufferedReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    /**
     * 截取text中指定段落
     *
     * @return
     */
    public static String subText(String text, String start, String end) {
        return text.substring(text.indexOf(start), text.lastIndexOf(end));
    }

    /**
     * 获取配置文件路径
     *
     * @param fileName
     * @return
     */
    public static URL getFileURL(String fileName) {
        ClassLoader loader = FileTool.class.getClassLoader();
        URL fileUrl = loader.getResource(fileName);
        return fileUrl;
    }
}

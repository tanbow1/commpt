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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {
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
            return FileUtil.input2byte(inputStream);
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
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(FileUtil.class.getResource(filePath).getPath()), ConsCommon.UTF8));
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
        ClassLoader loader = FileUtil.class.getClassLoader();
        URL fileUrl = loader.getResource(fileName);
        return fileUrl;
    }


    // =====================文件压缩=========================
    /*
     * //把文件压缩成zip File zipFile = new File("E:/demo.zip"); //定义输入文件流 InputStream
     * input = new FileInputStream(file); //定义压缩输出流 ZipOutputStream zipOut = null;
     * //实例化压缩输出流,并制定压缩文件的输出路径 就是E盘下,名字叫 demo.zip zipOut = new ZipOutputStream(new
     * FileOutputStream(zipFile)); zipOut.putNextEntry(new
     * ZipEntry(file.getName())); //设置注释 zipOut.setComment("www.demo.com"); int temp
     * = 0; while((temp = input.read()) != -1) { zipOut.write(temp); }
     * input.close(); zipOut.close();
     */
    // ==============================================

    /**
     * @param inputFileName 你要压缩的文件夹(整个完整路径)
     * @param zipFileName   压缩后的文件(整个完整路径)
     */
    public static void zip(String inputFileName, String zipFileName)
            throws Exception {
        zip(zipFileName, new File(inputFileName));
    }

    private static void zip(String zipFileName, File inputFile)
            throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                zipFileName));
        zip(out, inputFile, "");
        out.flush();
        out.close();
    }

    private static void zip(ZipOutputStream out, File f, String base)
            throws Exception {
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            out.putNextEntry(new ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + fl[i].getName());
            }
        } else {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream in = new FileInputStream(f);
            int b;
            // System.out.println(base);
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
        }
    }

    public static void main(String[] temp) {
        try {
            zip("D:\\ftl", "D:\\test.zip");// 要压缩的文件夹 和 压缩后的文件
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

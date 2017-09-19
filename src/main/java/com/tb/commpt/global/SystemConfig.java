package com.tb.commpt.global;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Tanbo on 2017/8/27.
 */

@Configuration
@PropertySource(value = "classpath:config/*.properties", ignoreResourceNotFound = true)
public class SystemConfig {

    @Value("${interceptor.ignoreUri}")
    public String INTERCEPTOR_IGNORE_URI;

    @Value("${jwt.secret}")
    public String JWT_SECRET;

    @Value("${file.default.maxFileLength}")
    public String FILE_MAXLENGTH;

    @Value("${file.default.maxUploadSize}")
    public String FILE_MAXUPLOADSIZE;

    @Value("${ftp.host}")
    public String FTP_HOST;

    @Value("${ftp.port}")
    public String FTP_PORT;

    @Value("${ftp.username}")
    public String FTP_USERNAME;

    @Value("${ftp.password}")
    public String FTP_PASSWORD;

    @Value("${ftp.uploadBasepath}")
    public String FTP_BASEPATH;
}

package com.tb.commpt.global;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Tanbo on 2017/8/27.
 */

@Configuration
@PropertySource(value = "classpath:config/config.properties")
public class SystemConfig {

    @Value("${interceptor.ignoreuri}")
    public String INTERCEPTOR_IGNORE_URI;

    @Value("${jwt.secret}")
    public String JWT_SECRET;

}

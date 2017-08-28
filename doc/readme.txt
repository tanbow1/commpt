springmvc+mybatis

目录结构
java:
    constant常量
    controller
        service
            1.dao //代码表等公共部分使用dao注入JdbcTemplate
            2.mapper(mapper.xml) //业务处理使用mapper

    exception
    filter
    interceptor
    logback
    model
    mybatis
    redis
    utils
    webservice
    global

resources:
    config
        config.properties //系统配置
        datasource.properties
        redis.properties
    i18n
    logger
    mybatis-config
    mybatis-generator
    spring-config
    spring-mvc
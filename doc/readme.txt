springmvc+mybatis

目录结构
java:
    annotation
    constant常量
    controller
        service
            1.dao //代码表等公共部分使用dao注入JdbcTemplate
            2.mapper(mapper.xml) //业务处理使用mapper

    exception
    filter
    global
    interceptor
    listener
    logback
    model
    redis
    utils


resources:
    config
        config.properties //系统配置
        datasource.properties //数据源相关

    i18n
    logger
    mybatis-config
    mybatis-generator
    spring-config
    spring-mq
    spring-mvc
    spring-redis
    ws-config


page:
    static
    templates
springmvc+mybatis

目录结构
java:
    annotation
    constant常量
    controller
        service
            1.dao //使用JdbcTemplate时
            2.mapper(mapper.xml) //使用mybatis时

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
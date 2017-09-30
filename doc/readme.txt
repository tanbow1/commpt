##公共服务后端

springmvc+mybatis

目录结构
java:
    commpt://后端主程序
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
    test:
    business://用户端程序


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
系统内页面均属于后台管理端，对于用户端，请移步：https://github.com/tanbow1/commptWeb

package com.hong.springboot.util.dynamicDataSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定特别数据源的注解，后期切面会根据注解里的信息动态创建数据源
 * @author Create by hongzh.zhang on 2021/3/19
 */
@Retention(RetentionPolicy.RUNTIME) // 在运行时也有效
@Target({ElementType.METHOD})  // 可作用于方法上
public @interface SpecifyDataSource {
    // 数据源名称
    String dataSourceName();

    // 数据源驱动(如：com.mysql.cj.jdbc.Driver)
    String driverClass();

    // 数据库url(如：jdbc:mysql://xxx:3306/xxx?useSSL=false&characterEncoding=UTF-8)
    String url();

    // 数据库用户名
    String username();

    // 数据库密码
    String password();
}

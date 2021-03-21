package com.hong.springboot.util.dynamicDataSource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

/**
 * 动态数据源切面类
 * 解析方法，如果上面有@SpecifyDataSource注解，那么就获取注解的值，根据注解的值动态创建数据源
 * @author create by hongzh.zhang on 2021-03-19
 */
@Aspect // 声明为切面，需要引入切面相关pom
@Component
@Slf4j
public class DynamicDataSourceAspect {

    @Autowired // 注入动态数据源bean
    private DynamicDataSource dynamicDataSource;


    // 声明切入点：也就是在哪会进行操作
    @Pointcut("@annotation(com.hong.springboot.util.dynamicDataSource.SpecifyDataSource)")
    private void pointcut() {
    }

    @Before("pointcut()") // 获取数据源基础信息&根据基础信息创建并切换数据源
    public void before(JoinPoint joinPoint) throws Exception {

        // 根据连接点获取到执行的类信息
        Class<?> clazz = joinPoint.getTarget().getClass();
        // 获取执行的方法
        String methodName = joinPoint.getSignature().getName();

        // 获取参数
        Class[] argClass = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();

        // 获取到方法
        Method method = clazz.getMethod(methodName, argClass);

        // 获取方法上的SpecifyDataSource注解
        SpecifyDataSource specifyDataSource = method.getAnnotation(SpecifyDataSource.class);
//        String dataSourceName = specifyDataSource.dataSourceName();
//        String driverClass = specifyDataSource.driverClass();
//        String url = specifyDataSource.url();
//        String username = specifyDataSource.username();
//        String password = specifyDataSource.password();
//
//        log.info("aaaa" + dataSourceName);

    }

    @After("pointcut()") // 最后切换为默认数据源
    public void after() {
        // 切换为默认数据源
        DataSourceContextHolder.removeDataSource();
    }





}

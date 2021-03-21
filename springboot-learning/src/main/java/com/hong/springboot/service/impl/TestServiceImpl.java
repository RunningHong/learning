package com.hong.springboot.service.impl;

import com.hong.springboot.mapper.TestMapper;
import com.hong.springboot.service.TestService;
import com.hong.springboot.util.WebResponse;
import com.hong.springboot.util.dynamicDataSource.DataSourceContextHolder;
import com.hong.springboot.util.dynamicDataSource.DynamicDataSource;
import com.hong.springboot.util.dynamicDataSource.SpecifyDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class TestServiceImpl implements TestService {

    @Autowired // 在DruidConfig中配置的bean
    DynamicDataSource dynamicDataSource;

    @Resource
    TestMapper testMapper;

    @Override
    public WebResponse dynamicDataSourceTest(String type) {
        String dataSourceName;
        String driverClass;
        String url;
        String username;
        String password;

        if ("1".equals(type) || "2".equals(type)) {
            if ("1".equals(type)) {
                dataSourceName="test1";
                driverClass="com.mysql.cj.jdbc.Driver";
                url="jdbc:mysql://localhost:3306/test1?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8";
                username="test1";
                password="test1";
            } else {
                dataSourceName = "test2";
                driverClass = "com.mysql.cj.jdbc.Driver";
                url = "jdbc:mysql://localhost:3306/test2?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8";
                username = "test1";
                password = "test1";
            }

            try {
                // 根据连接信息切换数据源
                dynamicDataSource.useDynamicDataSource(dataSourceName, driverClass,
                        url, username, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<Object> list = testMapper.getTestList();

        // 切换至默认数据源
        DataSourceContextHolder.removeDataSource();

        return WebResponse.success(list);
    }


    /**
     * ！！！记录一下AOP中会出现的问题：
     * 该方法无法使用到动态数据源的效果，即不管怎么样都只会使用默认的数据源
     * 方法内调用AOP的方法增强会失效(也就得不到注解上的信息)，详见AOP之动态代理原理
     * 解决方法详见：https://blog.csdn.net/fumushan/article/details/80090947
     * @param type
     * @return
     */
    public WebResponse dynamicDataSourceTest2(String type) {
        if ("1".equals(type)) {
            return dynamicDataSource1();
        } else if ("2".equals(type)) {
            return dynamicDataSource2();
        } else {
            List<Object> list = testMapper.getTestList();
            return WebResponse.success(list);
        }
    }



    // 使用注解声明动态数据源为test1
    @SpecifyDataSource(dataSourceName="test1", driverClass="com.mysql.cj.jdbc.Driver",
        url="jdbc:mysql://localhost:3306/test1?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8",
        username="test1", password="test1"
    )
    public WebResponse dynamicDataSource1() {
        List<Object> list = testMapper.getTestList();
        return WebResponse.success(list);
    }


    // 使用注解声明动态数据源为test2
    @SpecifyDataSource(dataSourceName="test2", driverClass="com.mysql.cj.jdbc.Driver",
            url="jdbc:mysql://localhost:3306/test2?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8",
            username="test1", password="test1"
    )
    public WebResponse dynamicDataSource2() {
        List<Object> list = testMapper.getTestList();
        return WebResponse.success(list);
    }


}

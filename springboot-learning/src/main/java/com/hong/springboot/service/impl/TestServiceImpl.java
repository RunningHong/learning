package com.hong.springboot.service.impl;

import com.hong.springboot.mapper.TestMapper;
import com.hong.springboot.service.TestService;
import com.hong.springboot.util.WebResponse;
import com.hong.springboot.util.dynamicDataSource.DynamicDataSource;

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

        if ("1".equals(type)) {
            dataSourceName="test1";
            driverClass="com.mysql.cj.jdbc.Driver";
            url="jdbc:mysql://localhost:3306/test1?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8";
            username="test1";
            password="test1";
        } else {
            dataSourceName="test2";
            driverClass="com.mysql.cj.jdbc.Driver";
            url="jdbc:mysql://localhost:3306/test2?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8";
            username="test1";
            password="test1";
        }

        try {
            // 根据连接信息切换数据源
            dynamicDataSource.initDynamicDataSource(dataSourceName, driverClass,
                                                    url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Object> list = testMapper.getTestList();



        return WebResponse.success(list);


    }
}

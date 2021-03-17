package com.hong.springboot.service.impl;

import com.hong.springboot.mapper.TestMapper;
import com.hong.springboot.service.TestService;
import com.hong.springboot.util.WebResponse;
import com.hong.springboot.util.dynamicDataSource.DynamicDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    DynamicDataSource dynamicDataSource;

    @Autowired
    TestMapper testMapper;

    @Override
    public WebResponse dynamicDataSourceTest(String type) {
        String dataSourceName;
        String driverClass;
        String url;
        String username;
        String password;

        if ("1".equals(type)) {
            dataSourceName="";
            driverClass="com.mysql.jdbc.Driver";
            url="";
            username="";
            password="";
        } else {
            dataSourceName="";
            driverClass="com.mysql.jdbc.Driver";
            url="";
            username="";
            password="";
        }


        try {
            // 根据连接信息切换数据源
            dynamicDataSource.initDynamicDataSource(dataSourceName, driverClass,
                                                    url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;


    }
}

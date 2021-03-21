package com.hong.springboot.service;

import com.hong.springboot.util.WebResponse;

public interface TestService {
    WebResponse dynamicDataSourceTest(String type);

    WebResponse dynamicDataSourceTest2(String type);

    WebResponse dynamicDataSource1();

    WebResponse dynamicDataSource2();

}

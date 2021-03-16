package com.hong.springboot.service.impl;

import com.hong.springboot.service.TestService;
import com.hong.springboot.util.WebResponse;

import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Override
    public WebResponse dynamicDataSourceTest(String type) {
        return null;


    }
}

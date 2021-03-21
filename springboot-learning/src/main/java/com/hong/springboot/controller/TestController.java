package com.hong.springboot.controller;

import com.hong.springboot.service.TestService;
import com.hong.springboot.util.WebResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * test
 */
@Controller
@RequestMapping(value = "test")
public class TestController {

    @Autowired
    TestService testService;


    /**
     * 测试接口
     */
    @ResponseBody
    @GetMapping("/test")
    public WebResponse dynamicDataSourceTest() {
        try {
            return WebResponse.success("测试接口");
        } catch (Exception e) {
            e.printStackTrace();
            return WebResponse.fail("数据出错了" + e.getMessage());
        }
    }


    /**
     * 动态数据源测试
     */
    @ResponseBody
    @GetMapping("/dynamicDataSourceTest")
    public WebResponse dynamicDataSourceTest(@RequestParam(value = "type") String type) {
        try {
            return testService.dynamicDataSourceTest(type);
        } catch (Exception e) {
            e.printStackTrace();
            return WebResponse.fail("数据出错了" + e.getMessage());
        }
    }


    /**
     * 动态数据源测试
     */
    @ResponseBody
    @GetMapping("/dynamicDataSourceTest2")
    public WebResponse dynamicDataSourceTest2(@RequestParam(value = "type") String type) {
        try {
            return testService.dynamicDataSourceTest2(type);
        } catch (Exception e) {
            e.printStackTrace();
            return WebResponse.fail("数据出错了" + e.getMessage());
        }
    }


    /**
     * 动态数据源测试1
     */
    @ResponseBody
    @GetMapping("/dynamicDataSource1")
    public WebResponse dynamicDataSource1() {
        try {
            return testService.dynamicDataSource1();
        } catch (Exception e) {
            e.printStackTrace();
            return WebResponse.fail("数据出错了" + e.getMessage());
        }
    }


    /**
     * 动态数据源测试2
     */
    @ResponseBody
    @GetMapping("/dynamicDataSource2")
    public WebResponse dynamicDataSource2() {
        try {
            return testService.dynamicDataSource2();
        } catch (Exception e) {
            e.printStackTrace();
            return WebResponse.fail("数据出错了" + e.getMessage());
        }
    }

}

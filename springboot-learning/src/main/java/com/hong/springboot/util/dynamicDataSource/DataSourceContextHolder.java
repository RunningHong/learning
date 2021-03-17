package com.hong.springboot.util.dynamicDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author create by hongzh.zhang on 2021-03-17
 * 数据源上下文，保存key值，用于动态切换使用
 * 用于给当前线程提供设置、获取、移除数据源key的操作
 */
@Slf4j // 使用lombook注入Slf4j
public class DataSourceContextHolder {

    /**
     * ThreadLocal 用于提供线程局部变量，在多线程环境可以保证各个线程里的变量独立于其它线程里的变量。
     * 这里采用ThreadLocal的主要原因是为了保证每个线程获取的数据源名称都是线程独属于这个线程的
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER =new ThreadLocal<>();

    /**
     * 给当前线程更换数据源
     * @param datasource
     */
    public static void setDataSource(String datasource){
        CONTEXT_HOLDER.set(datasource);
        log.info("已更换到数据源:{}", datasource);
    }

    /**
     * 获取数据源
     */
    public static String getDataSource(){
        return CONTEXT_HOLDER.get();
    }

    /**
     * 移除特定数据源，及切换到主数据源
     */
    public static void removeDataSource(){
        CONTEXT_HOLDER.remove();
        log.info("已切换到主数据源");
    }
}

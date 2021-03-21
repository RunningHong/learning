package com.hong.designPattern.proxy.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理处理器，调用被代理的对象的方法
 * @author hongzh.zhang on 2021/03/21
 */
public class PD03ProxyInvocationHandler implements InvocationHandler {

    // 被代理的接口，定义需要代理的行为
    private Object target;

    // 代理处理器需要注入被代理的对象
    public PD03ProxyInvocationHandler(Object target) {
        this.target = target;
    }

    // 重点：InvocationHandler.invoke(Object proxy, Method method, Object[] args) 用于调用被代理的方法
    // 调用被代理的方法，当然可以做一些其他事情
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();

        // 通过反射调用被代理的方法
        Object result = method.invoke(target, args);

        after();

        return result;
    }


    private void before() {
        System.out.println("中介：带人看房子");
    }

    private void after() {
        System.out.println("中介：收中介费");
    }

}

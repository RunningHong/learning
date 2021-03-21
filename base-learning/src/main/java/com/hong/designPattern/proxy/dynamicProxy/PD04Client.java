package com.hong.designPattern.proxy.dynamicProxy;

import java.lang.reflect.Proxy;

/**
 * 租房人
 * @author hongzh.zhang on 2021/03/21
 */
public class PD04Client {

    public static void main(String[] args) {
        // 房东角色，出租房屋，被代理的对象
        PD01Rent landlord = new PD02Landlord();

        // 代理处理器，帮被代理的角色做一些事情，做事情的时候可以附加做一些其他事情
        // 构造方法会注入被代理的对象
        PD03ProxyInvocationHandler proxyHandler = new PD03ProxyInvocationHandler(landlord);

        // 重点：Proxy用于生成我们最终的代理类对象
        // 生成代理对象
        PD01Rent proxy = (PD01Rent)Proxy.newProxyInstance(
                proxyHandler.getClass().getClassLoader(), // 指定类加载器
                landlord.getClass().getInterfaces(), // 指定需要被代理的类的接口
                proxyHandler);

        // 中介出租房屋
        proxy.rent();

        System.out.println("租房人：租到了房子");
    }
}

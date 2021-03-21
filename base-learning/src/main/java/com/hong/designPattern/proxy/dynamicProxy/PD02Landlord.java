package com.hong.designPattern.proxy.dynamicProxy;

/**
 * 房东-出租房屋的人
 * 房东需要出租房屋，所以需要实现P01Rent接口来用户出租房屋这个动作
 * @author hongzh.zhang on 2021/03/21
 */
public class PD02Landlord implements PD01Rent {
    @Override
    public void rent() {
        System.out.println("房东：出租房屋");
    }
}

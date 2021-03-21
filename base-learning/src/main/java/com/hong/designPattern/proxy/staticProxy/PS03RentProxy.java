package com.hong.designPattern.proxy.staticProxy;

/**
 * 出租房屋代理-租房中介
 * 代理需要向外出租房屋，所以也需要继承P01Rent接口，从而实现出租这个动作
 * @author hongzh.zhang on 2021/03/21
 */
public class PS03RentProxy implements PS01Rent {

    // 房主
    // 通过组合的形式放到代理里面，从而让代理帮我们做一些事情
    private PS02Landlord landlord;

    public PS03RentProxy() {

    }

    public PS03RentProxy(PS02Landlord landlord) {
        this.landlord = landlord;
    }

    @Override
    public void rent() {
        // 租房前做一些事情
        before();

        // 房主真实的把房租出去
        landlord.rent();

        // 租房后做一些事情
        after();
    }

    public void before() {
        System.out.println("中介：找房东拿房，带人看房。。。");
    }

    public void after() {
        System.out.println("中介：租房后续服务，催租。。。");
    }



}

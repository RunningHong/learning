package com.hong.designPattern.proxy.staticProxy;

/**
 * 租房人
 * @author hongzh.zhang on 2021/03/21
 */
public class PS04Client {

    public static void main(String[] args) {
        // 房东角色，出租房屋
        PS02Landlord landlord = new PS02Landlord();
        // 中介，代理房东，帮房东做一些事情
        PS03RentProxy rentProxy = new PS03RentProxy(landlord);

        // 中介出租房屋
        rentProxy.rent();

        System.out.println("租房人：租到了房子");
    }
}

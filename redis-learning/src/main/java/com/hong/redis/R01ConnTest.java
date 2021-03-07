package com.hong.redis;

import redis.clients.jedis.Jedis;

/**
 * @author hongzh.zhang on 2021/03/07
 * 连接测试
 * 修改redis.conf文件，将 bind 127.0.0.1这一行注释掉，
 * 或是将127.0.0.1修改为0.0.0.0（redis默认只支持本地连接，修改为0.0.0.0时，这样就可以支持外机连接了）
 */
public class R01ConnTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("node01", 6379);
        // 输出PONG，redis连通成功
        System.out.println(jedis.ping());
    }
}

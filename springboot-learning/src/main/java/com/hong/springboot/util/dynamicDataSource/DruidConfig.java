package com.hong.springboot.util.dynamicDataSource;

import com.alibaba.druid.pool.DruidDataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author create by hongzh.zhang on 2021-03-18
 */
@Configuration // 配置类，@Bean相当于spring的xml配置文件中的<bean>
@EnableTransactionManagement // 允许事务管理
@Slf4j
public class DruidConfig {

    @Value("${spring.datasource.url}") // 手动注入，参数值在application-xxx.yml中
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;


    @Bean // 得到druid数据源bean实例
    public DataSource druidDataSource() {
        // 创建druid数据源
        DruidDataSource druidDataSource = new DruidDataSource();

        // 基础连接信息
        druidDataSource.setUrl(this.dbUrl);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setDriverClassName(driverClassName);

        druidDataSource.setInitialSize(5);
        druidDataSource.setMinIdle(5);
        druidDataSource.setMaxActive(20);
        druidDataSource.setMaxWait(60000);
        // 是否缓存preparedStatement，也就是PSCache。
        // PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
        druidDataSource.setPoolPreparedStatements(false);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(50);

        return druidDataSource;
    }

    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dynamicDataSource() {
        log.info("注入bean： dynamicDataSource");
        DynamicDataSource dynamicDataSource = new DynamicDataSource();

        DataSource druidDataSource = druidDataSource(); // 得到druid数据库连接池实例
        dynamicDataSource.setDefaultTargetDataSource(druidDataSource); // 设置默认数据源

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("defaultDataSource", druidDataSource);
        dynamicDataSource.setTargetDataSources(targetDataSources); // 设置可路由的列表

        return dynamicDataSource;
    }

    @Bean // 声明SqlSessionFactoryBean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());

        return sqlSessionFactoryBean.getObject();
    }


}

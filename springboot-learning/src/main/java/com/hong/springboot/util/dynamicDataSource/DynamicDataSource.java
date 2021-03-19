package com.hong.springboot.util.dynamicDataSource;

import com.alibaba.druid.pool.DruidDataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * @author create by hongzh.zhang on 2021-03-17
 * 动态数据源
 * 继承spring框架的AbstractRoutingDataSource来实现动态数据源功能
 * 该类的bean实例在DruidConfig中创建
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 动态数据源-目标数据源map
     * 决定使用哪个数据源之前需要把多个数据源的信息以及默认数据源信息配置好
     */
    private Map<Object, Object> dynamicTargetDataSources = new ConcurrentHashMap<>();

    /**
     * 从DataSourceContextHolder拿到当前线程需要的数据源key
     * AbstractRoutingDataSource会根据数据源key去拿到已经注入AbstractRoutingDataSource.targetDataSources的数据库连接
     */
    @Override
    protected Object determineCurrentLookupKey() {
        // 从ThreadLocal中获取当前线程要使用的数据源key
        return DataSourceContextHolder.getDataSource();
    }


    /**
     * 根据传入的数据库信息初始化动态数据源
     * @param dataSourceName 数据源名称
     * @param driverClass 数据库驱动(如：com.mysql.jdbc.Driver)
     * @param url 数据库url(如：jdbc:mysql://xxx:3306/xxx?useSSL=false&characterEncoding=UTF-8)
     * @param username 数据库用户名
     * @param password 数据库密码
     */
    public void useDynamicDataSource(String dataSourceName, String driverClass,
                                     String url, String username, String password) throws Exception {

        if (!dynamicTargetDataSources.containsKey(dataSourceName)) { // 数据源未创建，创建数据源
            log.info("数据源【{}】未创建，准备创建数据源...", dataSourceName);
            try {
                Class.forName(driverClass); // 加载驱动类到内存中
                DriverManager.getConnection(url, username, password);

                DruidDataSource druidDataSource = new DruidDataSource(); // 创建数据库连接池
                druidDataSource.setName(dataSourceName);
                druidDataSource.setDriverClassName(driverClass);
                druidDataSource.setUrl(url);
                druidDataSource.setUsername(username);
                druidDataSource.setPassword(password);
                druidDataSource.setInitialSize(50); // 初始化时建立物理连接的个数
                druidDataSource.setMinIdle(40); // 最小连接池数量
                druidDataSource.setMaxActive(200); // 最大连接池数量
                druidDataSource.setMaxWait(60000); // 获取连接时最大等待时间，单位毫秒。

                // 初始化数据库连接池
                druidDataSource.init();

                // 向动态数据源-目标数据源map添加数据源
                this.dynamicTargetDataSources.put(dataSourceName, druidDataSource);

                // 将map赋值给父类(AbstractRoutingDataSource)的TargetDataSources
                super.setTargetDataSources(dynamicTargetDataSources);

                // 将TargetDataSources中的连接信息放入父类的resolvedDataSources管理, 通知spring有bean更新
                super.afterPropertiesSet();

                // 给线程上下文添加数据源key
                DataSourceContextHolder.setDataSource(dataSourceName);
            } catch (Exception e) {
                log.error("数据源【{}】创建失败", dataSourceName);
                e.printStackTrace();
            }
        } else { // 数据源已创建
            log.info("数据源【{}】已创建，测试连接是否正常...", dataSourceName);
            // 从目标数据源map获取到指定的数据源连接池
            DruidDataSource druidDataSource = (DruidDataSource)dynamicTargetDataSources.get(dataSourceName);

            Connection connection = null;
            try { // 测试数据源连接
                log.info("数据源【{}】->最大连接数【{}】，活跃连接数【{}】", dataSourceName,
                        druidDataSource.getPoolingCount(), druidDataSource.getActiveCount());
                connection = druidDataSource.getConnection();

                // 给线程上下文添加数据源key
                DataSourceContextHolder.setDataSource(dataSourceName);
            } catch(Exception e) {
                log.error("数据源【{}】连接测试失败", dataSourceName);
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
    }


}

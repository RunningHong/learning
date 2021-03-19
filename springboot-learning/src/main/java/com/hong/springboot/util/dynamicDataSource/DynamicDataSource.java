package com.hong.springboot.util.dynamicDataSource;

import com.alibaba.druid.pool.DruidDataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * @author create by hongzh.zhang on 2021-03-17
 * 动态数据源
 * 继承spring框架的AbstractRoutingDataSource来实现动态数据源功能
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 动态数据源-目标数据源map
     * 决定使用哪个数据源之前需要把多个数据源的信息以及默认数据源信息配置好
     */
    private Map<Object, Object> dynamicTargetDataSources = new ConcurrentHashMap<>();

    /**
     * 默认是使用spring配置的数据源key
     * 切换数据源只需要在DataSourceContextHolder.setDataSource();设置指定的数据源key即可
     * 框架会根据数据源key去创建对应的数据库连接
     */
    @Override
    protected Object determineCurrentLookupKey() {
        // 从ThreadLocal中获取当前线程要使用的数据源key
        String dataSource = DataSourceContextHolder.getDataSource();

        if (StringUtils.hasLength(dataSource)) { // 字符串是否不为空
            if (dynamicTargetDataSources.containsKey(dataSource)) {
                log.info("===当前数据源【{}】===", dataSource);
            } else {
                log.error("不存在的数据源【{}】!", dataSource);
            }
        } else {
            log.info("===当前数据源【默认数据源】===");
        }
        return dataSource;
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
            log.info("数据源【{}】未创建，准备创建数据源", dataSourceName);
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

                // 将TargetDataSources中的连接信息放入父类的resolvedDataSources管理, 通知spring有bean更新"
                super.afterPropertiesSet();

                // 给线程上下问题添加数据源key
                DataSourceContextHolder.setDataSource(dataSourceName);
            } catch (Exception e) {
                log.error("数据源【{}】创建失败", dataSourceName);
                e.printStackTrace();
            }
        } else { // 数据源已创建
            log.info("数据源【{}】已创建，准备测试数据是否正常...", dataSourceName);
            // 从目标数据源map获取到指定的数据源连接池
            DruidDataSource druidDataSource = (DruidDataSource)dynamicTargetDataSources.get(dataSourceName);

            Connection connection = null;
            try { // 测试数据源连接
//                log.info("数据源【{}】->最大连接数【{}】，闲置连接数【{}】", dataSourceName,
//                        druidDataSource.getPoolingCount(), druidDataSource.getActiveCount());
                connection = druidDataSource.getConnection();
//                log.info("数据源【{}】连接正常", dataSourceName);
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

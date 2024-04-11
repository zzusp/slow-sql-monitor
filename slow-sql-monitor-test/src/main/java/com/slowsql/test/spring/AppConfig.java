package com.slowsql.test.spring;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.slowsql.config.SlowSqlConfig;
import com.slowsql.spring.SlowSqlMonitorBeanProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        Properties properties = new Properties();
        properties.setProperty("driverClassName", "com.mysql.jdbc.Driver");
        properties.setProperty("url", "jdbc:mysql://192.168.1.10:3306/ase_release");
        properties.setProperty("username", "ase");
        properties.setProperty("password", "fEJ0CnDop54mQYB!");
        try {
            return DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public SlowSqlMonitorBeanProcessor slowSqlMonitor() {
        SlowSqlConfig config = new SlowSqlConfig();
        return new SlowSqlMonitorBeanProcessor(config);
    }

}

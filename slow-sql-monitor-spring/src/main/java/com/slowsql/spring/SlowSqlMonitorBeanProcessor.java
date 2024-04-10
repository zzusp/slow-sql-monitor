package com.slowsql.spring;

import com.slowsql.config.SlowSqlConfig;
import com.slowsql.pool.SlowSqlDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.PriorityOrdered;

import javax.sql.DataSource;

public class SlowSqlMonitorBeanProcessor implements PriorityOrdered, BeanPostProcessor {

    private final SlowSqlConfig slowSqlConfig;;

    public SlowSqlMonitorBeanProcessor(SlowSqlConfig slowSqlConfig) {
        this.slowSqlConfig = slowSqlConfig;
    }

    public int getOrder() {
        return 2147483647;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DataSource) {
            return new SlowSqlDataSource((DataSource) bean, slowSqlConfig);
        }
        return bean;
    }

}

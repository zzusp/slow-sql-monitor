package com.slowsql.spring;

import com.slowsql.config.SlowSqlConfig;
import com.slowsql.plugin.Interceptor;
import com.slowsql.pool.SlowSqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.sql.DataSource;

public class SlowSqlMonitorBeanProcessor implements BeanPostProcessor {

    private final static Logger logger = LoggerFactory.getLogger(SlowSqlMonitorBeanProcessor.class);

    private final SlowSqlConfig slowSqlConfig;

    public SlowSqlMonitorBeanProcessor(SlowSqlConfig slowSqlConfig) {
        logger.info("Init SlowSqlMonitorBeanProcessor");
        this.slowSqlConfig = slowSqlConfig;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Interceptor) {
            slowSqlConfig.getInterceptorChain().addInterceptor((Interceptor) bean);
        }
        if (bean instanceof DataSource) {
            logger.info("SlowSqlDataSource proxy dataSource: {} success", beanName);
            return new SlowSqlDataSource((DataSource) bean, slowSqlConfig);
        }
        return bean;
    }
}

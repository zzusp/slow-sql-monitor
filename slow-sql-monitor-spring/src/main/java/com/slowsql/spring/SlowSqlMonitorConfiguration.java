package com.slowsql.spring;

import com.slowsql.config.SlowSqlConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.lang.NonNull;

public class SlowSqlMonitorConfiguration implements BeanDefinitionRegistryPostProcessor {

    private final SlowSqlConfig slowSqlConfig;

    public SlowSqlMonitorConfiguration() {
        this.slowSqlConfig = new SlowSqlConfig();
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {

    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        SlowSqlMonitorBeanProcessor beanProcessor = new SlowSqlMonitorBeanProcessor(this.slowSqlConfig);
        configurableListableBeanFactory.addBeanPostProcessor(beanProcessor);
    }

    public SlowSqlConfig getSlowSqlConfig() {
        return slowSqlConfig;
    }
}

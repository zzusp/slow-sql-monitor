package com.slowsql.spring.boot.autoconfigure;

import com.slowsql.config.SlowSqlConfig;
import com.slowsql.spring.SlowSqlMonitorConfiguration;
import com.slowsql.spring.boot.autoconfigure.properties.SlowSqlProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnBean(SlowSqlMonitorConfiguration.class)
@Configuration
@EnableConfigurationProperties({SlowSqlProperties.class})
public class SlowSqlAutoConfiguration {

    @Bean
    public SlowSqlConfig slowSqlConfig(SlowSqlMonitorConfiguration configuration, SlowSqlProperties properties) {
        configuration.getSlowSqlConfig().setSlowMillis(properties.getSlowMillis());
        return configuration.getSlowSqlConfig();
    }

}

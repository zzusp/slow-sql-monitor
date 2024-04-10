package com.slowsql.spring.boot.autoconfigure;

import com.slowsql.config.SlowSqlConfig;
import com.slowsql.spring.SlowSqlMonitorBeanProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({SlowSqlProperties.class})
public class SlowSqlAutoConfiguration {

    @Bean
    public SlowSqlMonitorBeanProcessor slowSqlMonitor(SlowSqlProperties properties) {
        SlowSqlConfig config = new SlowSqlConfig();
        if (properties != null) {
            config.setSlowMillis(properties.getSlowMillis());
        }
        return new SlowSqlMonitorBeanProcessor(config);
    }

}

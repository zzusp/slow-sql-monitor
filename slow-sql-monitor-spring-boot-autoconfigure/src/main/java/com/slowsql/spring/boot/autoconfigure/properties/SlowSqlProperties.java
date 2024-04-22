package com.slowsql.spring.boot.autoconfigure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = SlowSqlProperties.SLOW_SQL_PREFIX)
public class SlowSqlProperties {

    public static final String SLOW_SQL_PREFIX = "slow-sql-monitor";

    private Long slowMillis = 1000L;

    public SlowSqlProperties() {
    }

    public Long getSlowMillis() {
        return slowMillis;
    }

    public void setSlowMillis(Long slowMillis) {
        this.slowMillis = slowMillis;
    }
}

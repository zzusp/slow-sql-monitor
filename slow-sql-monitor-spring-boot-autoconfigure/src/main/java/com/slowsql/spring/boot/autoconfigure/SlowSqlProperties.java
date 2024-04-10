package com.slowsql.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = SlowSqlProperties.SLOW_SQL_PREFIX)
public class SlowSqlProperties {

    public static final String SLOW_SQL_PREFIX = "slow-sql-monitor";

    private Long slowMillis;

    public SlowSqlProperties() {
        // 设置默认值
        this.slowMillis = 1000L;
    }

    public Long getSlowMillis() {
        return slowMillis;
    }

    public void setSlowMillis(Long slowMillis) {
        this.slowMillis = slowMillis;
    }
}

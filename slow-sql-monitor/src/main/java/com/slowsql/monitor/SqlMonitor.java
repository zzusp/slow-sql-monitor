package com.slowsql.monitor;

import com.slowsql.config.SlowSqlConfig;
import com.slowsql.plugin.Interceptor;

public class SqlMonitor {

    private SlowSqlConfig config;
    private String sql;
    private String params;
    private long startTime;
    // 单位纳秒
    private long duration;
    private long fetchRowCount;
    private boolean isSlowSql;

    public SqlMonitor(SlowSqlConfig config) {
        this.config = config;
    }

    public SqlMonitor(SlowSqlConfig config, String sql) {
        this.config = config;
        this.sql = sql;
    }

    public void beforeExecute() {
        this.startTime = System.nanoTime();
        this.fetchRowCount = 0;
        for (Interceptor innerInterceptor : this.config.getInterceptors()) {
            innerInterceptor.beforeExecute(this);
        }
    }

    public void afterExecute() {
        this.duration = System.nanoTime() - this.startTime;
        long millis = this.duration / (1000 * 1000);
        // 判断是否为慢sql
        if (millis >= config.getSlowMillis()) {
            this.isSlowSql = true;
        }
        for (Interceptor innerInterceptor : this.config.getInterceptors()) {
            innerInterceptor.afterExecute(this);
        }
    }

    public void closeExecute() {
        for (Interceptor innerInterceptor : this.config.getInterceptors()) {
            innerInterceptor.closeExecute(this);
        }
    }

    public void incrementRowCount() {
        this.fetchRowCount++;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public boolean isSlowSql() {
        return isSlowSql;
    }

    public void setSlowSql(boolean slowSql) {
        isSlowSql = slowSql;
    }

    public void clear() {
        this.sql = null;
        this.params = null;
        this.startTime = -1;
        this.duration = -1;
        this.fetchRowCount = -1;
        this.isSlowSql = false;
    }

    @Override
    public String toString() {
        return "SqlMonitor{" +
                "sql='" + sql + '\'' +
                ", params='" + params + '\'' +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", fetchRowCount=" + fetchRowCount +
                '}';
    }
}

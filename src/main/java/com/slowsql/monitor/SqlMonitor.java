package com.slowsql.monitor;

import com.slowsql.plugin.Interceptor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SqlMonitor {

    private List<Interceptor> interceptors;
    private String sql;
    private String params;
    private long startTime;
    private long duration;
    private long fetchRowCount;

    public SqlMonitor(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }

    public SqlMonitor(List<Interceptor> interceptors, String sql) {
        this.interceptors = interceptors;
        this.sql = sql;
    }

    public void beforeExecute() {
        this.startTime = System.nanoTime();
        for (Interceptor innerInterceptor : this.interceptors) {
            innerInterceptor.beforeExecute(this);
        }
    }

    public void afterExecute() {
        this.duration = System.nanoTime() - this.startTime;
        for (Interceptor innerInterceptor : this.interceptors) {
            innerInterceptor.afterExecute(this);
        }
    }

    public void fetchSize(ResultSet resultSet) throws SQLException {
        fetchRowCount = 0L;
        if (resultSet != null && resultSet.next()) {
            while (resultSet.next()) {
                fetchRowCount++;
            }
        }
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void clear() {
        this.sql = null;
        this.params = null;
        this.startTime = -1;
        this.duration = -1;
        this.fetchRowCount = -1;
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

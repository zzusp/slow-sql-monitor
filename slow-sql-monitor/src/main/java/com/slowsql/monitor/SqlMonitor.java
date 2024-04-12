package com.slowsql.monitor;

import com.slowsql.config.SlowSqlConfig;
import com.slowsql.plugin.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SqlMonitor {

    private final static Logger logger = LoggerFactory.getLogger(SqlMonitor.class);

    private final SlowSqlConfig config;
    private String sql;
    private List<String> params = new ArrayList<>();
    private long startTime;
    // 单位纳秒
    private long duration;
    // 结果集总行数
    private long fetchRowCount;
    private boolean isSlowSql;

    public SqlMonitor(SlowSqlConfig config) {
        this.config = config;
    }

    public SqlMonitor(SlowSqlConfig config, String sql) {
        this.config = config;
        this.sql = sql;
    }

    public void addParam(Object param) {
        try {
            if (param == null) {
                this.params.add("null");
            } else {
                String[] arr = param.getClass().getName().split("\\.");
                this.params.add(param.toString() + "(" + arr[arr.length - 1] + ")");
            }
        } catch (Exception e) {
            logger.error("add param {} error, message: {}", param, e.getMessage(), e);
        }
    }

    public void beforeExecute() {
        try {
            this.startTime = System.nanoTime();
            this.fetchRowCount = 0;
            for (Interceptor innerInterceptor : this.config.getInterceptorChain().getInterceptors()) {
                innerInterceptor.beforeExecute(this);
            }
        } catch (Exception e) {
            logger.error("before execute error, message: {}", e.getMessage(), e);
        }
    }

    public void afterExecute() {
        try {
            this.duration = System.nanoTime() - this.startTime;
            long millis = this.duration / (1000 * 1000);
            // 判断是否为慢sql
            if (millis >= config.getSlowMillis() && this.sql != null && this.sql.length() > 0) {
                this.isSlowSql = true;
            }
            for (Interceptor innerInterceptor : this.config.getInterceptorChain().getInterceptors()) {
                innerInterceptor.afterExecute(this);
            }
        } catch (Exception e) {
            logger.error("after execute error, message: {}", e.getMessage(), e);
        }
    }

    public void closeExecute() {
        try {
            for (Interceptor innerInterceptor : this.config.getInterceptorChain().getInterceptors()) {
                innerInterceptor.closeExecute(this);
            }
            this.clear();
        } catch (Exception e) {
            logger.error("close execute error, message: {}", e.getMessage(), e);
        }
    }

    public void incrementRowCount() {
        this.fetchRowCount++;
    }

    public String getSql() {
        return sql;
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

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getFetchRowCount() {
        return fetchRowCount;
    }

    public void setFetchRowCount(long fetchRowCount) {
        this.fetchRowCount = fetchRowCount;
    }

    public void clear() {
        this.sql = null;
        this.params.clear();
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

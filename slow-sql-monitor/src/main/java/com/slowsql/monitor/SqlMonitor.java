package com.slowsql.monitor;

import com.slowsql.config.SlowSqlConfig;
import com.slowsql.plugin.Interceptor;
import com.slowsql.stat.SlowSqlStat;
import com.slowsql.stat.pool.PoolStat;
import com.slowsql.stat.pool.PoolStatServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class SqlMonitor {

    private final static Logger logger = LoggerFactory.getLogger(SqlMonitor.class);

    private final DataSource dataSource;
    private final SlowSqlConfig config;
    private String sql;
    private List<String> params = new ArrayList<>();
    /**
     * 单位毫秒
     */
    private long startTime;
    /**
     * 单位毫秒
     */
    private long duration;
    /**
     * 结果集总行数
     */
    private long fetchRowCount;
    /**
     * 结果集数据大小
     */
    private long resultSize;
    private boolean isSlowSql;
    /**
     * 数据源连接池信息
     */
    private PoolStat poolStat;

    public SqlMonitor(DataSource dataSource, SlowSqlConfig config) {
        this.dataSource = dataSource;
        this.config = config;
    }

    public SqlMonitor(DataSource dataSource, SlowSqlConfig config, String sql) {
        this.dataSource = dataSource;
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
            this.startTime = System.currentTimeMillis();
            this.fetchRowCount = 0;
            this.resultSize = 0;
            this.poolStat = PoolStatServiceFactory.getPoolStat(this.dataSource);
            for (Interceptor innerInterceptor : this.config.getInterceptorChain().getInterceptors()) {
                innerInterceptor.beforeExecute(SlowSqlStat.build(this));
            }
        } catch (Exception e) {
            logger.error("before execute error, message: {}", e.getMessage(), e);
        }
    }

    public void afterExecute() {
        try {
            this.duration = System.currentTimeMillis() - this.startTime;
            // 判断是否为慢sql
            if (this.duration >= config.getSlowMillis() && this.sql != null && this.sql.length() > 0) {
                this.isSlowSql = true;
            }
            for (Interceptor innerInterceptor : this.config.getInterceptorChain().getInterceptors()) {
                innerInterceptor.afterExecute(SlowSqlStat.build(this));
            }
        } catch (Exception e) {
            logger.error("after execute error, message: {}", e.getMessage(), e);
        }
    }

    public void closeExecute() {
        try {
            for (Interceptor innerInterceptor : this.config.getInterceptorChain().getInterceptors()) {
                innerInterceptor.closeExecute(SlowSqlStat.build(this));
            }
            this.clear();
        } catch (Exception e) {
            logger.error("close execute error, message: {}", e.getMessage(), e);
        }
    }

    public void incrementRowCount() {
        this.fetchRowCount++;
    }

    public void incrementResultSize(long byteSize) {
        this.resultSize += byteSize;
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

    public long getResultSize() {
        return resultSize;
    }

    public void setResultSize(long resultSize) {
        this.resultSize = resultSize;
    }

    public SlowSqlConfig getConfig() {
        return config;
    }

    public PoolStat getPoolStat() {
        return poolStat;
    }

    public void clear() {
        this.sql = null;
        this.params.clear();
        this.startTime = -1;
        this.duration = -1;
        this.fetchRowCount = -1;
        this.resultSize = -1;
        this.isSlowSql = false;
        this.poolStat = new PoolStat();
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

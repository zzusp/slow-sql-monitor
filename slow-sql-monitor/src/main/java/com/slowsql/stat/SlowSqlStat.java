package com.slowsql.stat;

import com.slowsql.monitor.SqlMonitor;

import java.util.Date;

public class SlowSqlStat {

    private boolean isSlowSql;
    private String sql;
    private String params;
    private Date startTime;
    /**
     * 单位纳秒
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
    /**
     * 空闲连接数
     */
    private int idleCount;
    /**
     * 活跃连接数
     */
    private int activeCount;
    /**
     * 等待连接的线程数
     */
    private int waitCount;
    /**
     * 最大线程数
     */
    private int maxPoolSize;

    public SlowSqlStat() {
    }

    public static SlowSqlStat build(SqlMonitor monitor) {
        SlowSqlStat stat = new SlowSqlStat();
        stat.setSlowSql(monitor.isSlowSql());
        stat.setSql(monitor.getSql());
        stat.setParams(String.join(",", monitor.getParams()));
        stat.setStartTime(new Date(monitor.getStartTime()));
        stat.setDuration(monitor.getDuration());
        stat.setFetchRowCount(monitor.getFetchRowCount());
        stat.setResultSize(monitor.getResultSize());
        stat.setIdleCount(monitor.getPoolStat().getIdleCount());
        stat.setActiveCount(monitor.getPoolStat().getActiveCount());
        stat.setWaitCount(monitor.getPoolStat().getWaitCount());
        stat.setMaxPoolSize(monitor.getPoolStat().getMaxPoolSize());
        return stat;
    }

    public boolean isSlowSql() {
        return isSlowSql;
    }

    public void setSlowSql(boolean slowSql) {
        isSlowSql = slowSql;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
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

    public int getIdleCount() {
        return idleCount;
    }

    public void setIdleCount(int idleCount) {
        this.idleCount = idleCount;
    }

    public int getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(int activeCount) {
        this.activeCount = activeCount;
    }

    public int getWaitCount() {
        return waitCount;
    }

    public void setWaitCount(int waitCount) {
        this.waitCount = waitCount;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

}

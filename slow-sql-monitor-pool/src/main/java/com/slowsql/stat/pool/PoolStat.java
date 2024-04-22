package com.slowsql.stat.pool;

public class PoolStat {

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

    public PoolStat() {
        this.idleCount = -1;
        this.activeCount = -1;
        this.waitCount = -1;
        this.maxPoolSize = -1;
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

    @Override
    public String toString() {
        return "PoolMonitor{" +
                "idleCount=" + idleCount +
                ", activeCount=" + activeCount +
                ", waitCount=" + waitCount +
                ", maxPoolSize=" + maxPoolSize +
                '}';
    }

}

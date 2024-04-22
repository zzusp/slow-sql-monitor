package com.slowsql.stat.pool;

import javax.sql.DataSource;

public interface PoolStatService {

    boolean setDataSource(DataSource dataSource);
    /**
     * 空闲连接数
     */
    int getIdleCount();
    /**
     * 活跃连接数
     */
    int getActiveCount();
    /**
     * 等待连接的线程数
     */
    int getWaitCount();
    /**
     * 最大线程数
     */
    int getMaxPoolSize();

}

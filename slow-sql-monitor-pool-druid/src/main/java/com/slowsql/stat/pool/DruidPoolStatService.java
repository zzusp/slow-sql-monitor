package com.slowsql.stat.pool;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DruidPoolStatService implements PoolStatService {

    private DruidDataSource druidDataSource;

    @Override
    public boolean setDataSource(DataSource dataSource) {
        if (dataSource instanceof DruidDataSource) {
            this.druidDataSource = (DruidDataSource) dataSource;
            return true;
        }
        try {
            if (dataSource.isWrapperFor(DruidDataSource.class)) {
                this.druidDataSource = dataSource.unwrap(DruidDataSource.class);
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    @Override
    public int getIdleCount() {
        return druidDataSource.getPoolingCount();
    }

    @Override
    public int getActiveCount() {
        return druidDataSource.getActiveCount();
    }

    @Override
    public int getWaitCount() {
        return druidDataSource.getWaitThreadCount();
    }

    @Override
    public int getMaxPoolSize() {
        return druidDataSource.getMaxActive();
    }
}

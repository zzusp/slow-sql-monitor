package com.slowsql.stat.pool;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class HikariPoolStatService implements PoolStatService {

    private HikariDataSource hikariDataSource;

    @Override
    public boolean setDataSource(DataSource dataSource) {
        if (dataSource instanceof HikariDataSource) {
            this.hikariDataSource = (HikariDataSource) dataSource;
            return true;
        }
        try {
            if (dataSource.isWrapperFor(HikariDataSource.class)) {
                this.hikariDataSource = dataSource.unwrap(HikariDataSource.class);
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    @Override
    public int getIdleCount() {
        return hikariDataSource.getHikariPoolMXBean().getIdleConnections();
    }

    @Override
    public int getActiveCount() {
        return hikariDataSource.getHikariPoolMXBean().getActiveConnections();
    }

    @Override
    public int getWaitCount() {
        return hikariDataSource.getHikariPoolMXBean().getThreadsAwaitingConnection();
    }

    @Override
    public int getMaxPoolSize() {
        return hikariDataSource.getMaximumPoolSize();
    }
}

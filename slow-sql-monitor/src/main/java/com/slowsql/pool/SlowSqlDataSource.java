package com.slowsql.pool;

import com.slowsql.config.SlowSqlConfig;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class SlowSqlDataSource implements DataSource {

    private DataSource dataSource;
    private SlowSqlConfig config;

    public SlowSqlDataSource(DataSource dataSource, SlowSqlConfig config) {
        this.dataSource = dataSource;
        this.config = config;
    }

    public void setConfig(SlowSqlConfig config) {
        this.config = config;
    }

    public Connection getConnection() throws SQLException {
        return new SlowSqlConnection(dataSource.getConnection(), config);
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return new SlowSqlConnection(dataSource.getConnection(username, password), config);
    }

    public PrintWriter getLogWriter() throws SQLException {
        return dataSource.getLogWriter();
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        dataSource.setLogWriter(out);
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        dataSource.setLoginTimeout(seconds);
    }

    public int getLoginTimeout() throws SQLException {
        return dataSource.getLoginTimeout();
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return dataSource.getParentLogger();
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return dataSource.unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return dataSource.isWrapperFor(iface);
    }
}

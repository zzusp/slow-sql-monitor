package com.slowsql.pool;

import com.slowsql.plugin.Interceptor;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SlowSqlDataSource implements DataSource {

    private DataSource dataSource;
    private List<Interceptor> interceptors;

    private SlowSqlDataSource() {
    }

    public SlowSqlDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.interceptors = new ArrayList<>();
    }

    public SlowSqlDataSource(DataSource dataSource, List<Interceptor> interceptors) {
        this.dataSource = dataSource;
        this.interceptors = interceptors;
    }

    public Connection getConnection() throws SQLException {
        return new SlowSqlConnection(dataSource.getConnection(), interceptors);
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return new SlowSqlConnection(dataSource.getConnection(username, password), interceptors);
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

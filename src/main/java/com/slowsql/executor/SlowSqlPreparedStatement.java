package com.slowsql.executor;

import com.slowsql.plugin.Interceptor;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.List;

public class SlowSqlPreparedStatement implements PreparedStatement {

    private PreparedStatement preparedStatement;
    private List<Interceptor> interceptors;
    private String sql;
    private String params;
    private long startTime;
    private long duration;
    private long fetchRowCount;

    private SlowSqlPreparedStatement() {
    }

    public SlowSqlPreparedStatement(PreparedStatement preparedStatement, List<Interceptor> interceptors) {
        this.preparedStatement = preparedStatement;
        this.interceptors = interceptors;
    }

    private void beforeExecute() {
        this.startTime = System.nanoTime();
        for (Interceptor innerInterceptor : this.interceptors) {
            innerInterceptor.beforeExecute(this);
        }
    }

    private void afterExecute() {
        this.duration = System.nanoTime() - this.startTime;
        for (Interceptor innerInterceptor : this.interceptors) {
            innerInterceptor.afterExecute(this);
        }
    }

    private void fetchSize(ResultSet resultSet) throws SQLException {
        if (resultSet != null && resultSet.next()) {
            resultSet.last();
            fetchRowCount = resultSet.getRow();
            resultSet.beforeFirst();
        } else {
            fetchRowCount = 0L;
        }
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        // 记录日志
        this.beforeExecute();
        // 执行
        ResultSet resultSet = preparedStatement.executeQuery();
        // 总条数
        this.fetchSize(resultSet);
        // 记录日志
        this.afterExecute();
        return resultSet;
    }

    @Override
    public int executeUpdate() throws SQLException {
        // 记录日志
        this.beforeExecute();
        // 执行
        int resultSet = preparedStatement.executeUpdate();
        // 记录日志
        this.afterExecute();
        return resultSet;
    }

    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        preparedStatement.setNull(parameterIndex, sqlType);
    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        preparedStatement.setBoolean(parameterIndex, x);
    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {
        preparedStatement.setByte(parameterIndex, x);
    }

    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {
        preparedStatement.setShort(parameterIndex, x);
    }

    @Override
    public void setInt(int parameterIndex, int x) throws SQLException {
        preparedStatement.setInt(parameterIndex, x);
    }

    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {
        preparedStatement.setLong(parameterIndex, x);
    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {
        preparedStatement.setFloat(parameterIndex, x);
    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException {
        preparedStatement.setDouble(parameterIndex, x);
    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        preparedStatement.setBigDecimal(parameterIndex, x);
    }

    @Override
    public void setString(int parameterIndex, String x) throws SQLException {
        preparedStatement.setString(parameterIndex, x);
    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        preparedStatement.setBytes(parameterIndex, x);
    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {
        preparedStatement.setDate(parameterIndex, x);
    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {
        preparedStatement.setTime(parameterIndex, x);
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        preparedStatement.setTimestamp(parameterIndex, x);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        preparedStatement.setAsciiStream(parameterIndex, x, length);
    }

    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        preparedStatement.setUnicodeStream(parameterIndex, x, length);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        preparedStatement.setBinaryStream(parameterIndex, x, length);
    }

    @Override
    public void clearParameters() throws SQLException {
        preparedStatement.clearParameters();
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        preparedStatement.setObject(parameterIndex, x, targetSqlType);
    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {
        preparedStatement.setObject(parameterIndex, x);
    }

    @Override
    public boolean execute() throws SQLException {
        // 记录日志
        this.beforeExecute();
        // 执行
        boolean resultSet = preparedStatement.execute();
        // 记录日志
        this.afterExecute();
        return resultSet;
    }

    @Override
    public void addBatch() throws SQLException {
        // 记录日志
        this.beforeExecute();
        preparedStatement.addBatch();
        // 记录日志
        this.afterExecute();
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        preparedStatement.setCharacterStream(parameterIndex, reader, length);
    }

    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {
        preparedStatement.setRef(parameterIndex, x);
    }

    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        preparedStatement.setBlob(parameterIndex, x);
    }

    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {
        preparedStatement.setClob(parameterIndex, x);
    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {
        preparedStatement.setArray(parameterIndex, x);
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return preparedStatement.getMetaData();
    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        preparedStatement.setDate(parameterIndex, x, cal);
    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        preparedStatement.setTime(parameterIndex, x, cal);
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        preparedStatement.setTimestamp(parameterIndex, x, cal);
    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
        preparedStatement.setNull(parameterIndex, sqlType, typeName);
    }

    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {
        preparedStatement.setURL(parameterIndex, x);
    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return preparedStatement.getParameterMetaData();
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        preparedStatement.setRowId(parameterIndex, x);
    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {
        preparedStatement.setNString(parameterIndex, value);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        preparedStatement.setNCharacterStream(parameterIndex, value, length);
    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {

    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {

    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {

    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {

    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {

    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {

    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {

    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {

    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {

    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {

    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        this.sql = sql;
        // 记录日志
        this.beforeExecute();
        // 执行
        ResultSet resultSet = preparedStatement.executeQuery(sql);
        // 记录日志
        this.afterExecute();
        return resultSet;
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        this.sql = sql;
        // 记录日志
        this.beforeExecute();
        // 执行
        int resultSet = preparedStatement.executeUpdate(sql);
        // 记录日志
        this.afterExecute();
        return resultSet;
    }

    @Override
    public void close() throws SQLException {
        preparedStatement.close();
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        return 0;
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {

    }

    @Override
    public int getMaxRows() throws SQLException {
        return 0;
    }

    @Override
    public void setMaxRows(int max) throws SQLException {

    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {

    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return 0;
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {

    }

    @Override
    public void cancel() throws SQLException {

    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {

    }

    @Override
    public void setCursorName(String name) throws SQLException {

    }

    @Override
    public boolean execute(String sql) throws SQLException {
        this.sql = sql;
        // 记录日志
        this.beforeExecute();
        // 执行
        boolean resultSet = preparedStatement.execute(sql);
        // 记录日志
        this.afterExecute();
        return resultSet;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return preparedStatement.getResultSet();
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return preparedStatement.getUpdateCount();
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return preparedStatement.getMoreResults();
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {

    }

    @Override
    public int getFetchDirection() throws SQLException {
        return 0;
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {

    }

    @Override
    public int getFetchSize() throws SQLException {
        return 0;
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return 0;
    }

    @Override
    public int getResultSetType() throws SQLException {
        return 0;
    }

    @Override
    public void addBatch(String sql) throws SQLException {

    }

    @Override
    public void clearBatch() throws SQLException {

    }

    @Override
    public int[] executeBatch() throws SQLException {
        return new int[0];
    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return false;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return null;
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        return 0;
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return false;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return 0;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return false;
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {

    }

    @Override
    public boolean isPoolable() throws SQLException {
        return false;
    }

    @Override
    public void closeOnCompletion() throws SQLException {

    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}

package com.slowsql.plugin;

import com.slowsql.executor.SlowSqlPreparedStatement;

public interface Interceptor {

    void beforeExecute(SlowSqlPreparedStatement preparedStatement);

    void afterExecute(SlowSqlPreparedStatement preparedStatement);

}

package com.slowsql.plugin;

import com.slowsql.executor.SlowSqlPreparedStatement;
import com.slowsql.executor.SlowSqlStatement;
import com.slowsql.monitor.SqlMonitor;

public interface Interceptor {

    void beforeExecute(SqlMonitor sqlMonitor);

    void afterExecute(SqlMonitor sqlMonitor);

}

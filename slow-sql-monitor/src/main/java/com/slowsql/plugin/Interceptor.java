package com.slowsql.plugin;

import com.slowsql.monitor.SqlMonitor;

public interface Interceptor {

    void beforeExecute(SqlMonitor sqlMonitor);

    void afterExecute(SqlMonitor sqlMonitor);

    void closeExecute(SqlMonitor sqlMonitor);

}

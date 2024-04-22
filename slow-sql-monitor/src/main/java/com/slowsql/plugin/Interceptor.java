package com.slowsql.plugin;

import com.slowsql.stat.SlowSqlStat;

public interface Interceptor {

    void beforeExecute(SlowSqlStat slowSqlStat);

    void afterExecute(SlowSqlStat slowSqlStat);

    void closeExecute(SlowSqlStat slowSqlStat);

}

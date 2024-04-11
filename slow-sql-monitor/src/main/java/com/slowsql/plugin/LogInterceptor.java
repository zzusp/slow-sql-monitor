package com.slowsql.plugin;

import com.slowsql.monitor.SqlMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class LogInterceptor implements Interceptor {

    private final static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public void beforeExecute(SqlMonitor sqlMonitor) {

    }

    @Override
    public void afterExecute(SqlMonitor sqlMonitor) {

    }

    @Override
    public void closeExecute(SqlMonitor sqlMonitor) {
        if (sqlMonitor.isSlowSql()) {
            long duration = TimeUnit.NANOSECONDS.toMillis(sqlMonitor.getDuration());
            long start = TimeUnit.NANOSECONDS.toMillis(sqlMonitor.getStartTime());
            // Convert milliseconds to LocalDateTime
            LocalDateTime date = Instant.ofEpochMilli(start)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            logger.error("Time {} find slow sql {} millis, rows {}. sql: {}, param: {}", date.format(formatter), duration,
                    sqlMonitor.getFetchRowCount(), sqlMonitor.getSql(), String.join(", ", sqlMonitor.getParams()));
        }
    }
}

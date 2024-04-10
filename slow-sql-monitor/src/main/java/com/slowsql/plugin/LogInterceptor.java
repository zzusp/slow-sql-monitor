package com.slowsql.plugin;

import com.slowsql.monitor.SqlMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class LogInterceptor implements Interceptor {

    private final static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public void beforeExecute(SqlMonitor sqlMonitor) {

    }

    @Override
    public void afterExecute(SqlMonitor sqlMonitor) {

    }

    @Override
    public void closeExecute(SqlMonitor sqlMonitor) {
        long duration = TimeUnit.NANOSECONDS.toMillis(sqlMonitor.getDuration());
        if (duration >= sqlMonitor.getDuration()) {
            logger.error("slow sql {} millis. {}", duration, sqlMonitor.getSql());
        }
    }
}

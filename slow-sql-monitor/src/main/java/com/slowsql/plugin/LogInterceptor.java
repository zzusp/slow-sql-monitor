package com.slowsql.plugin;

import com.slowsql.stat.SlowSqlStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LogInterceptor implements Interceptor {

    private final static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public void beforeExecute(SlowSqlStat slowSqlStat) {

    }

    @Override
    public void afterExecute(SlowSqlStat slowSqlStat) {

    }

    @Override
    public void closeExecute(SlowSqlStat slowSqlStat) {
        if (slowSqlStat.isSlowSql()) {
            long duration = slowSqlStat.getDuration();
            long start = slowSqlStat.getStartTime().getTime();
            // Convert milliseconds to LocalDateTime
            LocalDateTime date = Instant.ofEpochMilli(start)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            long size = slowSqlStat.getResultSize();
            double kbSize = size > 0 ? BigDecimal.valueOf((double) size / 1024)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue() : 0;
            logger.error("Time {} find slow sql {} millis, rows {}. sql: {}, param: {}, size: {}KB, poolIdleCount: {}," +
                            " poolActiveCount: {}, poolWaitCount: {}, poolMaxPoolSize: {}", date.format(formatter),
                    duration, slowSqlStat.getFetchRowCount(), slowSqlStat.getSql(),
                    slowSqlStat.getParams(), kbSize, slowSqlStat.getIdleCount(),
                    slowSqlStat.getActiveCount(), slowSqlStat.getWaitCount(), slowSqlStat.getMaxPoolSize());
        }
    }
}

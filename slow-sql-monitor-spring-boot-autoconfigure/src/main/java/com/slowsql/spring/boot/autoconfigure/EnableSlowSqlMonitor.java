package com.slowsql.spring.boot.autoconfigure;

import com.slowsql.spring.SlowSqlMonitorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SlowSqlMonitorConfiguration.class})
public @interface EnableSlowSqlMonitor {
}

package com.slowsql.stat.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class PoolStatServiceFactory {

    private final static Logger logger = LoggerFactory.getLogger(PoolStatServiceFactory.class);

    private static final String DRUID_STAT_CLASS_NAME = "com.slowsql.stat.pool.DruidPoolStatService";
    private static final String HIKARI_STAT_CLASS_NAME = "com.slowsql.stat.pool.HikariPoolStatService";

    public static PoolStatService getPoolStatService(DataSource dataSource) {
        PoolStatService poolStatService;
        for (String className : Arrays.asList(DRUID_STAT_CLASS_NAME, HIKARI_STAT_CLASS_NAME)) {
            try {
                poolStatService = (PoolStatService) Class.forName(className).getDeclaredConstructor().newInstance();
                if (poolStatService.setDataSource(dataSource)) {
                    return poolStatService;
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException
                    | ClassNotFoundException e) {
                logger.debug("can not found service {}", className);
            }

        }
        logger.warn("unsupported datasource type: {}", dataSource.getClass().getName());

        return null;
    }

    public static PoolStat getPoolStat(DataSource dataSource) {
        PoolStat poolStat = new PoolStat();
        PoolStatService poolStatService = getPoolStatService(dataSource);
        if (poolStatService == null) {
            return poolStat;
        }
        poolStat.setActiveCount(poolStatService.getActiveCount());
        poolStat.setIdleCount(poolStatService.getIdleCount());
        poolStat.setWaitCount(poolStatService.getWaitCount());
        poolStat.setMaxPoolSize(poolStatService.getMaxPoolSize());
        return poolStat;
    }

}

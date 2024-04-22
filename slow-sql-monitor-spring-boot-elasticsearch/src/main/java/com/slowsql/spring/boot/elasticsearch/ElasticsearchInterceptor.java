package com.slowsql.spring.boot.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.slowsql.plugin.Interceptor;
import com.slowsql.spring.boot.elasticsearch.properties.SlowSqlElasticsearchProperties;
import com.slowsql.stat.SlowSqlStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ElasticsearchInterceptor implements Interceptor {

    private final static Logger logger = LoggerFactory.getLogger(ElasticsearchInterceptor.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final ElasticsearchClient elasticsearchClient;
    private final SlowSqlElasticsearchProperties properties;
    private final ThreadPoolTaskExecutor slowSqlThreadPool;

    public ElasticsearchInterceptor(ElasticsearchClient elasticsearchClient, SlowSqlElasticsearchProperties properties,
                                    ThreadPoolTaskExecutor slowSqlThreadPool) {
        this.elasticsearchClient = elasticsearchClient;
        this.properties = properties;
        this.slowSqlThreadPool = slowSqlThreadPool;
    }

    @Override
    public void beforeExecute(SlowSqlStat slowSqlStat) {

    }

    @Override
    public void afterExecute(SlowSqlStat slowSqlStat) {

    }

    @Override
    public void closeExecute(SlowSqlStat slowSqlStat) {
        if (slowSqlStat.getDuration() >= properties.getSendMillis()) {
            // 异步发送，避免性能影响
            slowSqlThreadPool.execute(() -> {
                try {
                    // 所有sql都发送，不止慢sql
                    this.elasticsearchClient.index(index -> index.index(properties.getIndexPrefix()
                            + LocalDate.now().format(formatter)).document(slowSqlStat));
                    logger.info("send sql data to es success");
                } catch (IOException e) {
                    logger.error("send sql data to es failed, message: {}", e.getMessage(), e);
                }
            });
        }
    }
}

package com.slowsql.spring.boot.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.slowsql.spring.boot.elasticsearch.properties.SlowSqlElasticsearchProperties;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ThreadPoolExecutor;

@ConditionalOnProperty(prefix = "slow-sql-monitor.elasticsearch",name = "enabled", havingValue = "true", matchIfMissing = true)
@Configuration
@EnableConfigurationProperties({SlowSqlElasticsearchProperties.class})
public class SlowSqlElasticsearchConfig {

    @Bean
    public ElasticsearchInterceptor elasticsearchInterceptor(ElasticsearchClient elasticsearchClient,
                                                             SlowSqlElasticsearchProperties properties) {
        return new ElasticsearchInterceptor(elasticsearchClient, properties, slowSqlThreadPool());
    }

    public ThreadPoolTaskExecutor slowSqlThreadPool() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心池大小
        taskExecutor.setCorePoolSize(5);
        // 最大线程数
        taskExecutor.setMaxPoolSize(10);
        // 队列程度
        taskExecutor.setQueueCapacity(100);
        // 线程空闲时间
        taskExecutor.setKeepAliveSeconds(60);
        // 线程前缀名称
        taskExecutor.setThreadNamePrefix("slowSqlThreadPool--");
        // 该方法用来设置 线程池关闭 的时候 等待 所有任务都完成后，再继续 销毁 其他的 Bean，
        // 这样这些 异步任务 的 销毁 就会先于 数据库连接池对象 的销毁。
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 任务的等待时间 如果超过这个时间还没有销毁就 强制销毁，以确保应用最后能够被关闭，而不是阻塞住。
        taskExecutor.setAwaitTerminationSeconds(300);
        // 线程不够用时由调用的线程处理该任务
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();
        return taskExecutor;
    }

    @ConditionalOnMissingBean
    @Bean
    public ElasticsearchClient elasticsearchClient(SlowSqlElasticsearchProperties properties) throws
            KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(properties.getUsername(), properties.getPassword()));

        SSLContextBuilder sslContextBuilder = SSLContexts.custom().loadTrustMaterial(null, (x509, s) -> true);

        final SSLContext sslContext = sslContextBuilder.build();
        HttpHost[] hosts = properties.getUris().stream().map(this::createHttpHost).toArray(HttpHost[]::new);
        RestClientBuilder restClientBuilder = RestClient.builder(hosts)
                .setHttpClientConfigCallback(httpAsyncClientBuilder -> httpAsyncClientBuilder.setSSLContext(sslContext)
                        .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                        .setDefaultCredentialsProvider(provider)).setRequestConfigCallback(builder -> builder
                        .setConnectTimeout((int) (properties.getConnectionTimeout().toMillis() / 1000))
                        .setSocketTimeout((int) (properties.getSocketTimeout().toMillis() / 1000)));
        if (properties.getPathPrefix() != null) {
            restClientBuilder.setPathPrefix(properties.getPathPrefix());
        }
        RestClient restClient = restClientBuilder.build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }

    private HttpHost createHttpHost(String uri) {
        try {
            return this.createHttpHost(URI.create(uri));
        } catch (IllegalArgumentException var3) {
            return HttpHost.create(uri);
        }
    }

    private HttpHost createHttpHost(URI uri) {
        if (!StringUtils.hasLength(uri.getUserInfo())) {
            return HttpHost.create(uri.toString());
        } else {
            try {
                return HttpHost.create((new URI(uri.getScheme(), null, uri.getHost(), uri.getPort(),
                        uri.getPath(), uri.getQuery(), uri.getFragment())).toString());
            } catch (URISyntaxException var3) {
                throw new IllegalStateException(var3);
            }
        }
    }

}

package com.slowsql.spring.boot.elasticsearch.properties;

import com.slowsql.spring.boot.autoconfigure.properties.SlowSqlProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ConfigurationProperties(prefix = SlowSqlElasticsearchProperties.SLOW_SQL_ES)
public class SlowSqlElasticsearchProperties {

    public static final String SLOW_SQL_ES = SlowSqlProperties.SLOW_SQL_PREFIX + ".elasticsearch";

    private Boolean enabled = true;
    private List<String> uris = new ArrayList<>(Collections.singletonList("http://localhost:9200"));
    private String username;
    private String password;
    private Duration connectionTimeout = Duration.ofSeconds(1L);
    private Duration socketTimeout = Duration.ofSeconds(30L);
    private String pathPrefix;
    private String indexPrefix = "slow_sql_";
    private Long sendMillis = 1000L;

    public SlowSqlElasticsearchProperties() {
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getUris() {
        return uris;
    }

    public void setUris(List<String> uris) {
        this.uris = uris;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Duration getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Duration connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Duration getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Duration socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public String getPathPrefix() {
        return pathPrefix;
    }

    public void setPathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }

    public String getIndexPrefix() {
        return indexPrefix;
    }

    public void setIndexPrefix(String indexPrefix) {
        this.indexPrefix = indexPrefix;
    }

    public Long getSendMillis() {
        return sendMillis;
    }

    public void setSendMillis(Long sendMillis) {
        this.sendMillis = sendMillis;
    }
}

# 慢SQL监控组件

## Usage

### Spring boot
```java

@SpringBootApplication
// 开启慢SQL监控
@EnableSlowSqlMonitor
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

}

```
application.yml
```yaml
slow-sql-monitor:
  slow-millis: 1000 # 单位毫秒
```
pom.xml
```xml
    <dependency>
        <groupId>com.slowsql</groupId>
        <artifactId>slow-sql-monitor-spring-boot-autoconfigure</artifactId>
        <version>1.0.0</version>
    </dependency>
```
### Spring
```java

@Configuration
public class AppConfig {

    @Bean
    public SlowSqlMonitorBeanProcessor slowSqlMonitor() {
        SlowSqlConfig config = new SlowSqlConfig();
        config.setSlowMillis(1000L);
        // 自定义拦截器
        CustomInterceptor interceptor = new CustomInterceptor();
        slowSqlConfig.getInterceptorChain().addInterceptor(interceptor);
        return new SlowSqlMonitorBeanProcessor(config);
    }

}

```
pom.xml
```xml
    <dependency>
        <groupId>com.slowsql</groupId>
        <artifactId>slow-sql-monitor-spring</artifactId>
        <version>1.0.0</version>
    </dependency>
```
### Without Spring
```java

public class SqlMonitor {

    public DataSource dataSource() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("driverClassName", "com.mysql.jdbc.Driver");
        properties.setProperty("url", "");
        properties.setProperty("username", "");
        properties.setProperty("password", "");
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        
        // 监控配置
        SlowSqlConfig slowSqlConfig = new SlowSqlConfig();
        slowSqlConfig.setSlowMillis(1000L);
        // 自定义拦截器
        CustomInterceptor interceptor = new CustomInterceptor();
        slowSqlConfig.getInterceptorChain().addInterceptor(interceptor);
        return new SlowSqlDataSource((DataSource) bean, slowSqlConfig);
    }
    
    // use the dataSource

}

```
pom.xml
```xml
    <dependency>
        <groupId>com.slowsql</groupId>
        <artifactId>slow-sql-monitor</artifactId>
        <version>1.0.0</version>
    </dependency>
```
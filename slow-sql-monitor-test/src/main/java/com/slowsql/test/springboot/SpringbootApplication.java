package com.slowsql.test.springboot;

import com.slowsql.spring.boot.autoconfigure.EnableSlowSqlMonitor;
import com.slowsql.test.UserMapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
@EnableSlowSqlMonitor
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(DataSource dataSource) {
        return args -> {
            // 在这里编写你的代码，它会在Spring Boot启动后执行
            System.out.println("Spring Boot 已启动完成，可以在这里执行你的逻辑...");
            System.out.println("datasource --- " + dataSource.getClass().getName());
            TransactionFactory transactionFactory =
                    new JdbcTransactionFactory();
            Environment environment =
                    new Environment("development", transactionFactory, dataSource);
            Configuration configuration = new Configuration(environment);
            configuration.addMapper(UserMapper.class);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
            SqlSession session = null;
            try {
                session = sqlSessionFactory.openSession();
                UserMapper userMapper = session.getMapper(UserMapper.class);
                userMapper.queryUserName("1");
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        };
    }


}

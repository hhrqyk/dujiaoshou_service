package com.qykhhr.dujiaoshouservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.qykhhr")
public class DujiaoshouserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DujiaoshouserviceApplication.class, args);
    }

}

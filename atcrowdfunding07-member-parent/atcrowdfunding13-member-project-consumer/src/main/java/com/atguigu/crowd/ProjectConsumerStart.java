package com.atguigu.crowd;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ProjectConsumerStart {
    public static void main(String[] args) {
        SpringApplication.run(ProjectConsumerStart.class,args);
    }
}

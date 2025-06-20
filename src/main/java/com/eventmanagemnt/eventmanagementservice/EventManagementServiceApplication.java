package com.eventmanagemnt.eventmanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.eventmanagemnt.eventmanagementservice.Repository")
public class EventManagementServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventManagementServiceApplication.class, args);
    }
}

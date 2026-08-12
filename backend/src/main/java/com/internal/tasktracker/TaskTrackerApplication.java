package com.internal.tasktracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskTrackerApplication {

    private static final Logger logger = LoggerFactory.getLogger(TaskTrackerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TaskTrackerApplication.class, args);
        logger.info("Task Tracker Application Started!");
    }
}

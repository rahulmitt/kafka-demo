package com.rahulmitt.interviewpedia.kafka;

import org.apache.kafka.connect.cli.ConnectStandalone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyKafkaConnectorApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyKafkaConnectorApplication.class);
        ConnectStandalone.main(args);
    }
}

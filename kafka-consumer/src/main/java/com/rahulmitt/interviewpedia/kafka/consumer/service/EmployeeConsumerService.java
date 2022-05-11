package com.rahulmitt.interviewpedia.kafka.consumer.service;

import com.rahulmitt.interviewpedia.kafka.domain.Employee;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmployeeConsumerService {
    @KafkaListener(topics = "first_topic", groupId = "app2", containerFactory = "kafkaListenerContainerFactory")
    public void consume(Employee employee) {
        System.out.println("Consumed message: " + employee);
    }
}

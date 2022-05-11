package com.rahulmitt.interviewpedia.kafka.producer.controller;

import com.rahulmitt.interviewpedia.kafka.domain.Department;
import com.rahulmitt.interviewpedia.kafka.domain.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/v1/employee")
public class EmployeeController {

    @Autowired
    private KafkaTemplate<Integer, Employee> kafkaTemplate;

    private static final String TOPIC = "first_topic";

    @Scheduled(fixedRateString = "${fixedRate:5000}")
    public void produce() {
        Employee employee = createEmployee(100, "Rahul", "Mittal");
        log.info(String.valueOf(employee));
        ProducerRecord<Integer, Employee> record = new ProducerRecord<>(TOPIC, employee.getId(), employee);
        ListenableFuture<SendResult<Integer, Employee>> future = kafkaTemplate.send(record);
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, Employee>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("unable to send message= {}", record, throwable);
            }

            @Override
            public void onSuccess(SendResult<Integer, Employee> integerEmployeeSendResult) {

            }
        });
    }

    @GetMapping("/{id}/{firstName}/{lastName}")
    public Employee createEmployee(@PathVariable int id, @PathVariable String firstName, @PathVariable String lastName) {
        Department department = Department.builder().id(1).name("Technology").build();
        Employee manager = Employee.builder().id(id).firstName("Neha").lastName("Mittal").department(department).build();

        return Employee.builder().id(id)
                .firstName(firstName)
                .lastName(lastName)
                .department(department)
                .managerId(1)
                .build();
    }
}

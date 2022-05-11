package com.rahulmitt.interviewpedia.kafka.controller;

import com.rahulmitt.interviewpedia.kafka.model.Department;
import com.rahulmitt.interviewpedia.kafka.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/v1/employee")
public class EmployeeController {

    private static int counter = 1;

    @GetMapping("/{id}/{firstName}/{lastName}")
    public Employee createEmployee(@PathVariable int id, @PathVariable String firstName, @PathVariable String lastName) {
        Department department = Department.builder().id(counter++).name("Technology").build();
        Employee manager = Employee.builder().id(id).firstName("Neha").lastName("Mittal").department(department).build();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e){}

        return Employee.builder().id(counter++)
                .firstName(firstName)
                .lastName(lastName)
                .department(department)
                .managerId(1)
                .build();
    }
}

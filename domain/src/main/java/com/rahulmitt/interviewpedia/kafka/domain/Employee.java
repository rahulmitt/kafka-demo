package com.rahulmitt.interviewpedia.kafka.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@NoArgsConstructor
@ToString
@Entity
public class Employee extends BaseEntity {
    private String firstName;
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    private Department department;
    private int managerId;

    @Builder
    public Employee(Integer id, String firstName, String lastName, Department department, int managerId) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.managerId = managerId;
    }
}

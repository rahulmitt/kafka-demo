package com.rahulmitt.interviewpedia.kafka.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;

@Getter
@NoArgsConstructor
@ToString
@Entity
public class Department extends BaseEntity {
    private String name;

    @Builder
    public Department(Integer id, String name) {
        super(id);
        this.name = name;
    }
}

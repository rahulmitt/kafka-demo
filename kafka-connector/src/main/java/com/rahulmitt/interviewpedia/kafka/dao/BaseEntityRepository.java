package com.rahulmitt.interviewpedia.kafka.dao;

import com.rahulmitt.interviewpedia.kafka.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseEntityRepository<T extends BaseEntity> extends JpaRepository<T, Integer> {

}

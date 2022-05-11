package com.rahulmitt.interviewpedia.kafka.connector.sink.h2;

import com.rahulmitt.interviewpedia.kafka.connector.sink.util.ConverterUtil;
import com.rahulmitt.interviewpedia.kafka.context.SpringContext;
import com.rahulmitt.interviewpedia.kafka.dao.BaseEntityRepository;
import com.rahulmitt.interviewpedia.kafka.model.BaseEntity;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Map;

public class H2SinkTask extends SinkTask {
    private static H2SinkConnectorConfig config;
    Logger logger = LoggerFactory.getLogger(H2SinkTask.class);

    @Autowired
    private BaseEntityRepository baseEntityRepository;

    @Override
    public void start(Map<String, String> map) {
        config = new H2SinkConnectorConfig(map);
        baseEntityRepository = SpringContext.getBean(BaseEntityRepository.class);
    }

    @Override
    public void put(Collection<SinkRecord> collection) {
        if (collection.isEmpty()) {
            logger.info("Records are empty");
            return;
        }
        for (SinkRecord record : collection) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            Schema keySchema = record.keySchema();
            Integer key = (Integer) record.key();

            Schema valueSchema = record.valueSchema();
            Struct value = (Struct) record.value();

            /*
            Employee employee = Employee.builder()
                    .id(value.getInt32("id"))
                    .firstName(value.getString("firstName"))
                    .lastName(value.getString("lastName"))
                    .department(
                            Department.builder()
                                    .id(value.getStruct("department").getInt32("id"))
                                    .name(value.getStruct("department").getString("name"))
                                    .build()
                    ).build();
            */

            try {
                BaseEntity entity = ConverterUtil.from(valueSchema, value);
                logger.info("KEY={} :: VALUE={}", key, entity);
                baseEntityRepository.save(entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void stop() {

    }

    @Override
    public String version() {
        return "kafka-sink-connector";
    }
}

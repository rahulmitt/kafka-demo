package com.rahulmitt.interviewpedia.kafka.consumer.config;

import com.rahulmitt.interviewpedia.kafka.domain.Employee;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfigs {
    public static final String BOOTSTRAP_SERVERS = "interviewpedia:9092";
    public static final String GROUP_ID = "app2";

    @Bean
    public ConsumerFactory<String, Employee> consumerFactory() {
        Map<String, Object> consumerProperties = new HashMap<>();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest"); // earliest, latest, none

        final JsonDeserializer<Employee> valueDeserializer = new JsonDeserializer<>();
        valueDeserializer.addTrustedPackages(
                "com.rahulmitt.interviewpedia.kafka.model"
        );

        return new DefaultKafkaConsumerFactory<>(consumerProperties, new StringDeserializer(), valueDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Employee> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Employee> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}

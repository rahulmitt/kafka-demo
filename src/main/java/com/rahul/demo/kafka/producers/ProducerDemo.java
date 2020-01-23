package com.rahul.demo.kafka.producers;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerDemo {
    public static void main(String[] args) {
        /*
            Steps:
                1. Create Producer properties
                    Go to https://kafka.apache.org/documentation/
                    Kafka 2.4 Documentation --> 3. Configuration --> 3.3 Producer Configs
                2. Create the producer
                3. Create producer record
                3. Send data
        */
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        // key/value serializer lets producer know what type of data you are sending to kafka
        // and how this should be serialized to bytes
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("first_topic", "hello World");
        producer.send(record);  // async
        producer.flush();   // flush data
        producer.close();   // flush and close
    }
}

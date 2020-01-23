package com.rahul.demo.kafka.producers;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerDemoKeys {

    private static Logger logger = LoggerFactory.getLogger(ProducerDemoKeys.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
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

        for (int i = 0; i < 10; i++) {
            String topic = "first_topic";
            String key = "id_" + i;
            String value = "Hello World " + i;

            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, value);
            // send() method is async. Hence, flush() is needed
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    // executes every time a record is successfully sent or exception is thrown
                    if (e == null) {
                        logger.info("Received new metadata: \n" +
                                "Key: " + key + "\n" +
                                "Topic: " + recordMetadata.topic() + "\n" +
                                "Partition: " + recordMetadata.partition() + "\n" +
                                "Offset: " + recordMetadata.offset() + "\n" +
                                "Timestamp: " + recordMetadata.timestamp());
                    } else {
                        logger.error("Error: ", e);
                    }
                }
            });
//            }).get();   // just to examplify. Block the send() to make it synchronous. Bad practice!!
        }
        producer.flush();   // flush data
        producer.close();   // flush and close
    }
}

package com.rahulmitt.interviewpedia.kafka.connector.sink.h2;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class H2SinkConnector extends SinkConnector {
    private H2SinkConnectorConfig config;

    @Override
    public void start(Map<String, String> map) {
        this.config = new H2SinkConnectorConfig(map);
    }

    @Override
    public Class<? extends Task> taskClass() {
        return H2SinkTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int i) {
        List<Map<String, String>> configs = new ArrayList<>(1);
        configs.add(config.originalsStrings());     // originalsStrings() returns back the same map in start()
        return configs;
    }

    @Override
    public void stop() {

    }

    @Override
    public ConfigDef config() {
        return H2SinkConnectorConfig.conf();
    }

    @Override
    public String version() {
        return "kafka-sink-connector";
    }
}

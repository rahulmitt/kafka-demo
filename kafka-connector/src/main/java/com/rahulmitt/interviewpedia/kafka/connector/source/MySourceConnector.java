package com.rahulmitt.interviewpedia.kafka.connector.source;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySourceConnector extends SourceConnector {
    private final Logger log = LoggerFactory.getLogger(MySourceConnector.class);

    private MySourceConnectorConfig config;

    @Override
    public void start(Map<String, String> map) {
        this.config = new MySourceConnectorConfig(map);
    }

    @Override
    public Class<? extends Task> taskClass() {
        return MySourceTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int i) {   // i is task.max in connector properties
        // Define the individual task configurations that will be executed.
        List<Map<String, String>> configs = new ArrayList<>(1);
        configs.add(config.originalsStrings());     // originalsStrings() returns back the same map in start()
        return configs;
    }

    @Override
    public void stop() {
        // Do things that are necessary to stop your connector.
        // nothing is necessary to stop for this connector

        // eg., if a DB connection is opened in start(), they should be closed in stop()
    }

    @Override
    public ConfigDef config() {
        return MySourceConnectorConfig.conf();
    }

    @Override
    public String version() {
        return "kafka-source-connector";
    }
}

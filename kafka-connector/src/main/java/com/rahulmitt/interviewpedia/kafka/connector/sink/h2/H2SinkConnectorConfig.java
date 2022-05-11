package com.rahulmitt.interviewpedia.kafka.connector.sink.h2;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

public class H2SinkConnectorConfig extends AbstractConfig {

    public static final String EMPLOYEE_TOPIC = "topics";
    public static final String EMPLOYEE_TOPIC_DOC = "topic to read from";

    public H2SinkConnectorConfig(Map<?, ?> originals) {
        this(conf(), originals);
    }

    public H2SinkConnectorConfig(ConfigDef definition, Map<?, ?> originals) {
        super(definition, originals);
    }

    public static ConfigDef conf() {
        return new ConfigDef()
                .define(EMPLOYEE_TOPIC, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, EMPLOYEE_TOPIC_DOC);
    }

    public String getEmployeeTopic() {
        return this.getString(EMPLOYEE_TOPIC);
    }
}

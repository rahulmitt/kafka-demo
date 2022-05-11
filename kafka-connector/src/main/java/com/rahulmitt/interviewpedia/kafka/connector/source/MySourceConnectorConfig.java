package com.rahulmitt.interviewpedia.kafka.connector.source;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

public class MySourceConnectorConfig extends AbstractConfig {

    public static final String EMPLOYEE_TOPIC = "employee.topic";
    public static final String EMPLOYEE_TOPIC_DOC = "topic to push";

    public static final String EMPLOYEE_HTTP_URL = "employee.http.url";
    public static final String EMPLOYEE_HTTP_URL_DOC = "url to poll";

    public MySourceConnectorConfig(Map<?, ?> originals) {
        this(conf(), originals);
    }

    public MySourceConnectorConfig(ConfigDef definition, Map<?, ?> originals) {
        super(definition, originals);
    }

    public static ConfigDef conf() {
        return new ConfigDef()
                .define(EMPLOYEE_TOPIC, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, EMPLOYEE_TOPIC_DOC)
                .define(EMPLOYEE_HTTP_URL, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, EMPLOYEE_HTTP_URL_DOC);
    }

    public String getEmployeeTopic() {
        return this.getString(EMPLOYEE_TOPIC);
    }

    public String getEmployeeHttpUrl() {
        return this.getString(EMPLOYEE_HTTP_URL);
    }

}

package com.rahulmitt.interviewpedia.kafka.schema;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

public class MySchema {

    public static final Schema DEPT_SCHEMA = SchemaBuilder.struct().name("com.rahulmitt.interviewpedia.kafka.model.Department")
            .version(1)
            .field("id", Schema.INT32_SCHEMA)
            .field("name", Schema.STRING_SCHEMA)
            .build();

    public static final Schema EMPLOYEE_SCHEMA = SchemaBuilder.struct().name("com.rahulmitt.interviewpedia.kafka.model.Employee")
            .version(1)
            .field("id", Schema.INT32_SCHEMA)
            .field("firstName", Schema.STRING_SCHEMA)
            .field("lastName", Schema.STRING_SCHEMA)
            .field("department", DEPT_SCHEMA)
            .build();
}

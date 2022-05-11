package com.rahulmitt.interviewpedia.kafka.connector.source;

import com.rahulmitt.interviewpedia.kafka.context.SpringContext;
import com.rahulmitt.interviewpedia.kafka.model.Department;
import com.rahulmitt.interviewpedia.kafka.model.Employee;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rahulmitt.interviewpedia.kafka.schema.MySchema.DEPT_SCHEMA;
import static com.rahulmitt.interviewpedia.kafka.schema.MySchema.EMPLOYEE_SCHEMA;

public class MySourceTask extends SourceTask {

    private static MySourceConnectorConfig config;

    private RestTemplate restTemplate;

    @Override
    public String version() {
        return null;
    }

    @Override
    public void start(Map<String, String> map) {
        config = new MySourceConnectorConfig(map);
        restTemplate = SpringContext.getBean(RestTemplate.class);
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        List<SourceRecord> records = new ArrayList<>();
        ResponseEntity<Employee> responseEntity = restTemplate.exchange(config.getEmployeeHttpUrl(), HttpMethod.GET, null, Employee.class);
        Employee employee = responseEntity.getBody();
        records.add(buildSourceRecord(employee));
        Thread.sleep(1000);
        return records;
    }

    @Override
    public void stop() {

    }

    private static SourceRecord buildSourceRecord(Employee employee) {
        return new SourceRecord(
                sourcePartition(employee),
                sourceOffset(employee),
                config.getEmployeeTopic(),
                null, // partition will be inferred by the framework
                Schema.INT32_SCHEMA,        // buildRecordKey(employee),
                employee.getId(),
                EMPLOYEE_SCHEMA,
                buildRecordValue(employee),
                System.currentTimeMillis());
    }

    private static Map<String, String> sourcePartition(Employee employee) {
        Map<String, String> map = new HashMap<>();
        map.put("id", employee.getId() + "");
        return map;
    }

    private static Map<String, String> sourceOffset(Employee employee) {
        Map<String, String> map = new HashMap<>();
        map.put("id", employee.getId() + "");
        return map;
    }

    private static Struct buildRecordKey(Employee employee) {
        return new Struct(Schema.STRING_SCHEMA)
                .put("id", employee.getId() + "");
    }

    public static Struct buildRecordValue(Employee employee) {
        Department department = employee.getDepartment();

        Struct deptStruct = new Struct(DEPT_SCHEMA)
                .put("id", department.getId())
                .put("name", department.getName());

        return new Struct(EMPLOYEE_SCHEMA)
                .put("id", employee.getId())
                .put("firstName", employee.getFirstName())
                .put("lastName", employee.getLastName())
                .put("department", deptStruct);
    }
}

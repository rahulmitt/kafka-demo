package com.rahulmitt.interviewpedia.kafka.connector.sink.util;

import com.rahulmitt.interviewpedia.kafka.model.BaseEntity;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;

public class ConverterUtil {

    private static boolean setProperty(Object object, String fieldName, Object fieldValue) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, fieldValue);
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }

    public static BaseEntity from(Schema schema, Struct value)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class clazz = Class.forName(schema.name());
        BaseEntity object = (BaseEntity) clazz.newInstance();

        for (Field field : schema.fields()) {
            switch (field.schema().type()) {
                case INT32:
                    setProperty(object, field.name(), value.getInt32(field.name()));
                    break;

                case STRING:
                    setProperty(object, field.name(), value.getString(field.name()));
                    break;

                case STRUCT:
                    setProperty(object, field.name(), from(field.schema(), value.getStruct(field.name())));
                    break;

                default:
                    System.out.println("");
                    break;
            }
        }

        return object;
    }
}

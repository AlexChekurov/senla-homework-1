package com.example.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ApplicationContext {
    private final Map<Class<?>, Object> beans = new HashMap<>();
    private final Properties properties = new Properties();

    public void addBean(Class<?> type, Object instance) {
        beans.put(type, instance);
    }

    public <T> T getBean(Class<T> type) {
        for (Map.Entry<Class<?>, Object> entry : beans.entrySet()) {
            if (type.isAssignableFrom(entry.getKey())) {
                return type.cast(entry.getValue());
            }
        }
        throw new RuntimeException("No bean found for type: " + type.getName());
    }

    public Iterable<Object> getAllBeans() {
        return beans.values();
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties.putAll(properties);
    }
}

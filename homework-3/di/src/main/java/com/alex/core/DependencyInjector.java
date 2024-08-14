package com.alex.core;

import com.alex.annotations.Autowire;
import com.alex.annotations.Value;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

public class DependencyInjector {
    private final ApplicationContext context;
    private final Properties properties;

    public DependencyInjector(ApplicationContext context, Properties properties) {
        this.context = context;
        this.properties = properties;
    }

    public void injectDependencies() {
        for (Object bean : context.getAllBeans()) {
            injectFields(bean);
            injectSetters(bean);
        }
    }

    private void injectFields(Object bean) {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowire.class)) {
                Object dependency = context.getBean(field.getType());
                field.setAccessible(true);
                try {
                    field.set(bean, dependency);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            if (field.isAnnotationPresent(Value.class)) {
                Value valueAnnotation = field.getAnnotation(Value.class);
                String value = properties.getProperty(valueAnnotation.value());
                field.setAccessible(true);
                try {
                    field.set(bean, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void injectSetters(Object bean) {
        Method[] methods = bean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Autowire.class)) {
                Class<?>[] paramTypes = method.getParameterTypes();
                Object[] dependencies = new Object[paramTypes.length];
                for (int i = 0; i < paramTypes.length; i++) {
                    dependencies[i] = context.getBean(paramTypes[i]);
                }
                try {
                    method.invoke(bean, dependencies);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

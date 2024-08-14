package com.alex.core;

import com.alex.annotations.Autowire;
import com.alex.annotations.Component;
import org.reflections.Reflections;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.*;


public final class ApplicationContextInitializer {
    private static final Properties properties = new Properties();

    private ApplicationContextInitializer() {}

    public static ApplicationContext initializeContext(String basePackage, Class<?> mainClass) {
        ApplicationContext context = new ApplicationContext();
        loadProperties(mainClass);
        List<Class<?>> allComponents = scanAllComponents(basePackage);

        // Создаем и регистрируем компоненты без зависимостей (дефолтные конструкторы)
        for (Class<?> component : allComponents) {
            if (findAutowiredConstructor(component) == null) {
                createInstance(component, context);
            }
        }

        // Создаем и регистрируем компоненты с конструкторами, требующими инъекций
        for (Class<?> component : allComponents) {
            if (findAutowiredConstructor(component) != null) {
                createInstance(component, context);
            }
        }

        // Инжектируем зависимости
        injectDependencies(context);
        return context;
    }

    private static void loadProperties(Class<?> mainClass) {
        try (InputStream input = mainClass.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                return;
            }
            try (InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
                properties.load(reader);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static List<Class<?>> scanAllComponents(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> components = reflections.getTypesAnnotatedWith(Component.class);
        return new ArrayList<>(components);
    }

    private static void createInstance(Class<?> component, ApplicationContext context) {
        try {
            Constructor<?> constructor = findAutowiredConstructor(component);
            Object instance;

            if (constructor != null) {
                // Создание объекта с использованием конструктора с @Autowire
                Class<?>[] paramTypes = constructor.getParameterTypes();
                Object[] params = new Object[paramTypes.length];

                for (int i = 0; i < paramTypes.length; i++) {
                    params[i] = context.getBean(paramTypes[i]);
                }

                instance = constructor.newInstance(params);
            } else {
                // Создание объекта с использованием дефолтного конструктора
                instance = component.getDeclaredConstructor().newInstance();
            }

            context.addBean(component, instance);
            System.out.println("Created and added bean: " + component.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void injectDependencies(ApplicationContext context) {
        DependencyInjector injector = new DependencyInjector(context, properties);
        injector.injectDependencies();
    }

    private static Constructor<?> findAutowiredConstructor(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(Autowire.class)) {
                return constructor;
            }
        }
        return null;
    }
}

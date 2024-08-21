package com.example.core;

import com.example.annotations.Autowire;
import com.example.annotations.Component;
import org.reflections.Reflections;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class ApplicationContextInitializer {

    private ApplicationContextInitializer() {
    }

    public static ApplicationContext initializeContext(String basePackage, Class<?> mainClass) {
        ApplicationContext context = new ApplicationContext();
        Properties properties = loadProperties(mainClass);
        context.setProperties(properties);

        List<Class<?>> allComponents = scanAllComponents(basePackage);

        initializeComponents(allComponents, context);

        injectDependencies(context);
        return context;
    }

    private static void initializeComponents(List<Class<?>> allComponents, ApplicationContext context) {
        allComponents.stream()
                .sorted(Comparator.comparingInt(component -> findAutowiredConstructor(component).isPresent() ? 1 : -1))
                .forEach(component -> createInstance(component, context));
    }

    private static Properties loadProperties(Class<?> mainClass) {
        Properties properties = new Properties();
        try (InputStream input = mainClass.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                return properties;
            }
            try (InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
                properties.load(reader);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return properties;
    }

    private static List<Class<?>> scanAllComponents(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> components = reflections.getTypesAnnotatedWith(Component.class);
        return new ArrayList<>(components);
    }

    private static void createInstance(Class<?> component, ApplicationContext context) {
        try {
            Optional<Constructor<?>> optionalConstructor = findAutowiredConstructor(component);
            Object instance;

            if (optionalConstructor.isPresent()) {
                Constructor<?> constructor = optionalConstructor.get();
                // Create an object using a constructor with @Autowire
                Class<?>[] paramTypes = constructor.getParameterTypes();
                Object[] params = new Object[paramTypes.length];

                for (int i = 0; i < paramTypes.length; i++) {
                    params[i] = context.getBean(paramTypes[i]);
                }

                instance = constructor.newInstance(params);
            } else {
                // Create an object using the default constructor
                instance = component.getDeclaredConstructor().newInstance();
            }

            context.addBean(component, instance);
            System.out.println("Created and added bean: " + component.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void injectDependencies(ApplicationContext context) {
        DependencyInjector injector = new DependencyInjector(context);
        injector.injectDependencies();
    }

    private static Optional<Constructor<?>> findAutowiredConstructor(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredConstructors())
                .filter(constructor -> constructor.isAnnotationPresent(Autowire.class))
                .findFirst();
    }

}

package com.alex.homework4example;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.controller.*;
import com.alex.homework4example.dao.MockDataInitializer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            MockDataInitializer mockDataInitializer = context.getBean(MockDataInitializer.class);
            mockDataInitializer.initialize();

            UserController userController = context.getBean(UserController.class);
            userController.initializeAndPerformOperations();

            CustomerController customerController = context.getBean(CustomerController.class);
            customerController.initializeAndPerformOperations();

            AccountController accountController = context.getBean(AccountController.class);
            accountController.initializeAndPerformOperations();

            CardController cardController = context.getBean(CardController.class);
            cardController.initializeAndPerformOperations();

            TransactionController transactionController = context.getBean(TransactionController.class);
            transactionController.initializeAndPerformOperations();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

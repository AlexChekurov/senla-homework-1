package com.alex.homework4example;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.controller.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            UserController userController = context.getBean(UserController.class);
            CustomerController customerController = context.getBean(CustomerController.class);
            AccountController accountController = context.getBean(AccountController.class);
            CardController cardController = context.getBean(CardController.class);
            TransactionController transactionController = context.getBean(TransactionController.class);

            // Операции с пользователями
            System.out.println("Creating Users...");
            userController.createUsers();

            System.out.println("\nReading User 1...");
            userController.readUser(1L);

            System.out.println("\nUpdating User 1...");
            userController.updateUser(1L);

            // Операции с клиентами
            System.out.println("\nCreating Customers...");
            customerController.createCustomers();

            System.out.println("\nReading Customer 1...");
            customerController.readCustomer(1L);

            System.out.println("\nUpdating Customer 1...");
            customerController.updateCustomer(1L);

            // Операции с аккаунтами
            System.out.println("\nCreating Accounts...");
            accountController.createAccounts();

            System.out.println("\nReading Account 1...");
            accountController.readAccount(1L);

            System.out.println("\nUpdating Account 1...");
            accountController.updateAccount(1L);

            // Операции с картами
            System.out.println("\nCreating Cards...");
            cardController.createCards();

            System.out.println("\nReading Card 1...");
            cardController.readCard(1L);

            System.out.println("\nUpdating Card 1...");
            cardController.updateCard(1L);

            System.out.println("\nDeleting Card 2...");
            cardController.deleteCard(2L);

            // Операции с транзакциями
            System.out.println("\nCreating Transactions...");
            transactionController.createTransactions();

            System.out.println("\nReading Transaction 1...");
            transactionController.readTransaction(1L);

            System.out.println("\nUpdating Transaction 1...");
            transactionController.updateTransaction(1L);

            System.out.println("\nDeleting Transaction 2...");
            transactionController.deleteTransaction(2L);

            // Только после всех операций с картами и транзакциями удаляем клиента 2
            System.out.println("\nDeleting Customer 2...");
            customerController.deleteCustomer(2L);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

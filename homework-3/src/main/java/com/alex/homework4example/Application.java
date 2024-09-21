package com.alex.homework4example;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.controller.AccountController;
import com.alex.homework4example.controller.CustomerController;
import com.alex.homework4example.controller.RoleController;
import com.alex.homework4example.controller.UserController;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.entity.Customer;
import com.alex.homework4example.entity.Role;
import com.alex.homework4example.entity.User;
import com.alex.homework4example.exception.EntityNotFoundException;
import com.alex.homework4example.exception.InsufficientFundsException;
import com.alex.homework4example.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            AccountService accountService = context.getBean(AccountService.class);
            AccountController accountController = context.getBean(AccountController.class);

            var roles = createRolesMap(context);
            var users = createUsers(context, roles);
            createCustomers(users.get(0), context);
            findAllCustomers(context);

            // Создание двух аккаунтов для перевода
            System.out.println("Создание аккаунтов...");
            List<Account> accounts = accountController.createAccounts();
//
            Account account1 = accounts.get(0);
            Account account2 = accounts.get(1);

            System.out.println("\nБаланс до перевода:");
            System.out.println("Баланс счета отправителя: " + account1.getBalance());
            System.out.println("Баланс счета получателя: " + account2.getBalance());

            // Выполняем перевод средств
            System.out.println("\nПеревод 500.00 от счета 1 к счету 2...");
            accountService.transferMoney(account1, account2, new BigDecimal("500.00"));

//             Получаем обновленные данные аккаунтов
            Account fromAccount = accountService.findById(account1.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Счет отправителя не найден"));
            Account toAccount = accountService.findById(account2.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Счет получателя не найден"));
//
            System.out.println("\nБаланс после успешного перевода:");
            System.out.println("Баланс счета отправителя: " + fromAccount.getBalance());
            System.out.println("Баланс счета получателя: " + toAccount.getBalance());
//
//             Попытка перевести сумму, превышающую баланс
            try {
                System.out.println("\nПопытка перевода 2000.00 от счета 1 к счету 2...");
                accountService.transferMoney(fromAccount, toAccount, new BigDecimal("2000.00"));
            } catch (InsufficientFundsException e) {
                System.out.println("Перевод не выполнен: " + e.getMessage());
            }

//             Проверяем балансы после неудачного перевода
            fromAccount = accountService.findById(account1.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Счет отправителя не найден"));
            toAccount = accountService.findById(account2.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Счет получателя не найден"));
//
            System.out.println("\nБаланс после неудачного перевода:");
            System.out.println("Баланс счета отправителя: " + fromAccount.getBalance());
            System.out.println("Баланс счета получателя: " + toAccount.getBalance());
//
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Customer> findAllCustomers(ApplicationContext context) {
        var customerController = context.getBean(CustomerController.class);
        var customers = customerController.findAllCustomers();
        System.out.println(customers);
        return customers;
    }

    private static void createCustomers(User user, ApplicationContext context) throws JsonProcessingException {
        var customerController = context.getBean(CustomerController.class);
        customerController.createCustomers(user);
    }

    private static List<User> createUsers(ApplicationContext context, Map<String, Role> roles) throws JsonProcessingException {
        var userController = context.getBean(UserController.class);
        var users = userController.createUsers(roles);
        System.out.println("Созданы пользователи: " + users);
        return users;
    }

    private static Map<String, Role> createRolesMap(AnnotationConfigApplicationContext context) {
        var rolesController = context.getBean(RoleController.class);
        var result = rolesController.createAllRoles().stream()
                .collect(Collectors.toMap(
                        role -> role.getName().name(),
                        Function.identity()
                ));
        System.out.println("Созданы роли: " + result);
        return result;
    }
}
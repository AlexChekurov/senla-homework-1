package com.alex.homework4example.dao;

import com.alex.homework4example.entity.*;
import com.alex.homework4example.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class MockDataInitializer {

    private final UserServiceImpl userService;
    private final CustomerServiceImpl customerService;
    private final AccountServiceImpl accountService;
    private final TransactionServiceImpl transactionService;
    private final CardServiceImpl cardService;

    @Autowired
    public MockDataInitializer(UserServiceImpl userService, CustomerServiceImpl customerService,
                               AccountServiceImpl accountService, TransactionServiceImpl transactionService,
                               CardServiceImpl cardService) {
        this.userService = userService;
        this.customerService = customerService;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.cardService = cardService;
    }

    public void initialize() {
        // Создаем пользователей
        User admin = User.builder()
                .username("admin")
                .password("admin123")
                .role(Role.ADMIN)
                .build();

        User customerUser = User.builder()
                .username("customer")
                .password("cust123")
                .role(Role.CUSTOMER)
                .build();

        admin = userService.create(admin);
        customerUser = userService.create(customerUser);

        // Создаем клиента
        Customer customer = Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .street("Main Street")
                .city("New York")
                .state("NY")
                .postalCode("10001")
                .country("USA")
                .createdAt(LocalDateTime.now())
                .user(customerUser)
                .build();

        customer = customerService.create(customer);

        // Создаем банковский счет
        Account account = Account.builder()
                .accountNumber("123456789")
                .accountType("SAVINGS")
                .balance(new BigDecimal("1000.00"))
                .currency("USD")
                .iban("US12345678901234567890")
                .createdAt(LocalDateTime.now())
                .build();

        account = accountService.create(account);

        // Связываем счет с клиентом
        customer.getAccounts().add(account);
        customerService.update(customer);

        // Создаем транзакцию
        Transaction transaction = Transaction.builder()
                .amount(new BigDecimal("200.00"))
                .transactionDate(LocalDateTime.now())
                .sourceAccount(account)
                .destinationAccount(account)
                .currency("USD")
                .build();

        transactionService.create(transaction);

        // Создаем карту
        Card card = Card.builder()
                .account(account)
                .customer(customer)
                .cardNumber("1234-5678-9012-3456")
                .cardType("VISA")
                .expirationDate(LocalDate.now().plusYears(2))
                .cvv("123")
                .createdAt(LocalDateTime.now())
                .build();

        cardService.create(card);

        // Добавляем карту в аккаунт
        account.getCards().add(card);
        accountService.update(account);

        System.out.println("Mock data initialized successfully!");
    }
}

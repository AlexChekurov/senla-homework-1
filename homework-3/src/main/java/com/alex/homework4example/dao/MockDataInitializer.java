package com.alex.homework4example.dao;

import com.alex.homework4example.dto.*;
import com.alex.homework4example.entity.Role;
import com.alex.homework4example.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class MockDataInitializer {

    private final UserService userService;
    private final CustomerService customerService;
    private final AccountService accountService;
    private final CustomerAccountService customerAccountService;
    private final TransactionService transactionService;
    private final CardService cardService;

    @Autowired
    public MockDataInitializer(UserService userService, CustomerService customerService,
                               AccountService accountService, CustomerAccountService customerAccountService,
                               TransactionService transactionService, CardService cardService) {
        this.userService = userService;
        this.customerService = customerService;
        this.accountService = accountService;
        this.customerAccountService = customerAccountService;
        this.transactionService = transactionService;
        this.cardService = cardService;
    }

    public void initialize() {
        UserDTO admin = UserDTO.builder()
                .username("admin")
                .password("admin123")
                .role(String.valueOf(Role.ADMIN))
                .build();

        UserDTO customer = UserDTO.builder()
                .username("customer")
                .password("cust123")
                .role(String.valueOf(Role.CUSTOMER))
                .build();

        admin = userService.create(admin);
        customer = userService.create(customer);

        CustomerDTO customer1 = CustomerDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .street("Main Street")
                .city("New York")
                .state("NY")
                .postalCode("10001")
                .country("USA")
                .userId(customer.getId())
                .build();

        customer1 = customerService.create(customer1);

        AccountDTO account1 = AccountDTO.builder()
                .accountNumber("123456789")
                .accountType("SAVINGS")
                .balance(new BigDecimal("1000.00"))
                .currency("USD")
                .iban("US12345678901234567890")
                .build();

        account1 = accountService.create(account1);

        CustomerAccountDTO customerAccount1 = CustomerAccountDTO.builder()
                .customerId(customer1.getId())
                .accountId(account1.getId())
                .build();

        customerAccountService.create(customerAccount1);

        TransactionDTO transaction1 = TransactionDTO.builder()
                .amount(new BigDecimal("200.00"))
                .transactionDate(LocalDateTime.now())
                .sourceAccountId(account1.getId())
                .destinationAccountId(account1.getId())
                .currency("USD")
                .build();

        transactionService.create(transaction1);

        CardDTO card1 = CardDTO.builder()
                .accountId(account1.getId())
                .customerId(customer1.getId())
                .cardNumber("1234-5678-9012-3456")
                .cardType("VISA")
                .expirationDate(LocalDate.now().plusYears(2))
                .cvv("123")
                .build();

        cardService.create(card1);
    }
}
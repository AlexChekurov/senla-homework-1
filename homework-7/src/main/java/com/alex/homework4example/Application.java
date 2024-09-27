package com.alex.homework4example;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.exception.EntityNotFoundException;
import com.alex.homework4example.exception.InsufficientFundsException;
import com.alex.homework4example.repository.AccountRepository;
import com.alex.homework4example.repository.AddressRepository;
import com.alex.homework4example.repository.CustomerRepository;
import com.alex.homework4example.repository.RoleRepository;
import com.alex.homework4example.repository.UserRepository;
import com.alex.homework4example.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.List;

import static com.alex.homework4example.utils.mock.MockDataGenerator.generateTestAccount;
import static com.alex.homework4example.utils.mock.MockDataGenerator.generateTestAddress;
import static com.alex.homework4example.utils.mock.MockDataGenerator.generateTestCustomer;
import static com.alex.homework4example.utils.mock.MockDataGenerator.generateTestRole;
import static com.alex.homework4example.utils.mock.MockDataGenerator.generateTestUser;

public class Application {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            var accounts = generateAccounts(context);
            var account1 = accounts.get(0);
            var account2 = accounts.get(1);
            System.out.println("Balance before transfer:");
            System.out.println("Sender's account balance: " + account1.getBalance());
            System.out.println("Recipient's account balance: " + account2.getBalance());

            System.out.println("Transferring 500.00 from account 1 to account 2...");
            var accountService = context.getBean(AccountService.class);
            accountService.transferMoney(account1, account2, new BigDecimal("500.00"));

            Account fromAccount = accountService.findById(account1.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Sender's account not found"));
            Account toAccount = accountService.findById(account2.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Recipient's account not found"));

            System.out.println("Balances after successful transfer:");
            System.out.println("Sender's account balance: " + fromAccount.getBalance());
            System.out.println("Recipient's account balance: " + toAccount.getBalance());

            try {
                System.out.println("Attempting to transfer 2000.00 from account 1 to account 2...");
                accountService.transferMoney(fromAccount, toAccount, new BigDecimal("2000.00"));
            } catch (InsufficientFundsException e) {
                System.out.println("Transfer failed: " + e.getMessage());
            }

            fromAccount = accountService.findById(account1.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Sender's account not found"));
            toAccount = accountService.findById(account2.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Recipient's account not found"));

            System.out.println("Balances after failed transfer:");
            System.out.println("Sender's account balance: " + fromAccount.getBalance());
            System.out.println("Recipient's account balance: " + toAccount.getBalance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Account> generateAccounts(ApplicationContext context) {
        var role = context.getBean(RoleRepository.class).create(generateTestRole());
        var address = context.getBean(AddressRepository.class).create(generateTestAddress());

        var user = context.getBean(UserRepository.class).create(generateTestUser(role));
        var customer = context.getBean(CustomerRepository.class).create(generateTestCustomer(address, user));

        System.out.println("Generating accounts...");
        return List.of(
                context.getBean(AccountRepository.class).create(generateTestAccount(customer)),
                context.getBean(AccountRepository.class).create(generateTestAccount(customer)));
    }
}

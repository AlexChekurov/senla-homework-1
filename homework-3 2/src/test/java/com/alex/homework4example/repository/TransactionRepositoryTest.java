package com.alex.homework4example.repository;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.entity.Customer;
import com.alex.homework4example.entity.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Transactional
@Rollback
@SpringJUnitConfig(classes = { AppConfig.class, DataBaseConfig.class })
@TestPropertySource(locations = "classpath:application-test.properties")
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    void testCreateTransaction() {
        // when
        Transaction transaction = createTestTransaction();

        // then
        assertNotNull(transaction.getId(), "Transaction ID should not be null after creation");
    }

    @Test
    void testFindById() {
        // given
        Transaction transaction = createTestTransaction();

        // when
        Optional<Transaction> foundTransaction = transactionRepository.findById(transaction.getId());

        // then
        assertTrue(foundTransaction.isPresent(), "Transaction should be found");
        assertEquals(transaction.getId(), foundTransaction.get().getId(), "The IDs should match");
    }

    @Test
    void testUpdateTransaction() {
        // given
        Transaction transaction = createTestTransaction();
        transaction.setAmount(BigDecimal.valueOf(200.00));
        transactionRepository.update(transaction);

        // when
        Optional<Transaction> updatedTransaction = transactionRepository.findById(transaction.getId());

        // then
        assertTrue(updatedTransaction.isPresent(), "Transaction should be found after update");
        assertEquals(0, updatedTransaction.get().getAmount().compareTo(BigDecimal.valueOf(200.00)),
                "Transaction amount should be updated");
    }

    @Test
    void testDeleteTransaction() {
        // given
        Transaction transaction = createTestTransaction();
        Optional<Transaction> foundTransactionBeforeDeletion = transactionRepository.findById(transaction.getId());
        assertTrue(foundTransactionBeforeDeletion.isPresent(), "Transaction should exist before deletion");

        // when
        transactionRepository.deleteById(transaction.getId());

        // then
        Optional<Transaction> deletedTransaction = transactionRepository.findById(transaction.getId());
        assertFalse(deletedTransaction.isPresent(), "Transaction should be deleted and not found in the database");
    }

    @Test
    void testFindAllTransactions() {
        // given
        createTestTransaction();
        createTestTransaction();

        // when
        List<Transaction> transactions = transactionRepository.findAll();

        // then
        assertEquals(2, transactions.size(), "There should be two transactions in the database");
    }

    private Customer createTestCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail(UUID.randomUUID() + "@example.com");
        customer.setPhone(UUID.randomUUID().toString().substring(0, 15));
        customerRepository.create(customer);
        return customer;
    }

    private Account createTestAccount() {
        Account account = new Account();
        account.setAccountNumber(UUID.randomUUID().toString().substring(0, 20));
        account.setAccountType("Savings");
        account.setBalance(BigDecimal.valueOf(1000.00));
        account.setCurrency("USD");
        account.setIban(UUID.randomUUID().toString().substring(0, 28));
        account.setCreatedAt(LocalDateTime.now());

        Customer customer = createTestCustomer();
        account.setCustomer(customer);

        accountRepository.create(account);
        return account;
    }

    private Transaction createTestTransaction() {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(100.00));
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setCurrency("USD");

        Set<Account> accounts = new HashSet<>();
        accounts.add(createTestAccount()); // Добавляем один тестовый аккаунт
        transaction.setAccounts(accounts);

        transactionRepository.create(transaction);
        return transaction;
    }

}

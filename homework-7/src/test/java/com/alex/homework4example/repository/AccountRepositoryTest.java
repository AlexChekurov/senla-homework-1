package com.alex.homework4example.repository;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@Rollback
@SpringJUnitConfig(classes = {AppConfig.class, DataBaseConfig.class})
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application-test.properties")
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Random random;

    @BeforeEach
    public void setUp() {
        random = new Random();

        // Очистка базы данных перед каждым тестом
        accountRepository.deleteAll();
        customerRepository.deleteAll(); // Убедитесь, что репозиторий клиентов также очищен
    }

    private String generateUniqueAccountNumber() {
        return "ACC" + random.nextInt(1000000);
    }

    private Account createTestAccount() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe" + random.nextInt(1000000) + "@example.com");
        customer.setPhone(String.valueOf(random.nextInt(10000)));

        customerRepository.create(customer);

        Account account = new Account();
        account.setAccountNumber(generateUniqueAccountNumber());
        account.setAccountType("SAVINGS");
        account.setBalance(new BigDecimal("1000.00"));
        account.setCurrency("USD");
        account.setIban("US" + random.nextInt(10000000));
        account.setCustomer(customer);
        account.setCreatedAt(LocalDateTime.now());

        accountRepository.create(account);
        return account;
    }

    private void createTestAccounts(int count) {
        for (int i = 0; i < count; i++) {
            createTestAccount();
        }
    }

    @Test
    public void testCreateAccount() {
        Account account = createTestAccount();
        assertNotNull(account.getId());
    }

    @Test
    public void testFindById() {
        Account account = createTestAccount();
        Optional<Account> foundAccount = accountRepository.findById(account.getId());
        assertTrue(foundAccount.isPresent());
        assertEquals(account.getId(), foundAccount.get().getId());
    }

    @Test
    public void testUpdateAccount() {
        Account account = createTestAccount();
        account.setBalance(new BigDecimal("2000.00"));
        accountRepository.update(account);

        Optional<Account> updatedAccount = accountRepository.findById(account.getId());
        assertTrue(updatedAccount.isPresent());
        assertEquals(new BigDecimal("2000.00"), updatedAccount.get().getBalance());
    }

    @Test
    public void testDeleteAccount() {
        Account account = createTestAccount();

        Optional<Account> foundAccountBeforeDeletion = accountRepository.findById(account.getId());
        assertTrue(foundAccountBeforeDeletion.isPresent(), "Account should exist before deletion");

        accountRepository.deleteById(account.getId());

        Optional<Account> deletedAccount = accountRepository.findById(account.getId());
        assertFalse(deletedAccount.isPresent(), "Account should be deleted and not found in the database");
    }

    @Test
    public void testFindAllWithCustomerFetch() {
        createTestAccounts(2); // Создать 2 тестовых аккаунта
        List<Account> accounts = accountRepository.findAllWithCustomerFetch();
        assertEquals(2, accounts.size());
        assertEquals("John", accounts.get(0).getCustomer().getFirstName());
    }

    @Test
    public void testFindAllWithCustomerFetchJPQL() {
        createTestAccounts(2); // Создать 2 тестовых аккаунта
        List<Account> accounts = accountRepository.findAllWithCustomerFetchJPQL();
        assertEquals(2, accounts.size());
        assertEquals("John", accounts.get(0).getCustomer().getFirstName());
    }

    @Test
    public void testFindAllWithCustomerEntityGraph() {
        createTestAccounts(2); // Создать 2 тестовых аккаунта
        List<Account> accounts = accountRepository.findAllWithCustomerEntityGraph();
        assertEquals(2, accounts.size());
        assertEquals("John", accounts.get(0).getCustomer().getFirstName());
    }
}

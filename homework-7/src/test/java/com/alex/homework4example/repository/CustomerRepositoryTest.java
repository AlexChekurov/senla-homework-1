package com.alex.homework4example.repository;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Transactional
@Rollback
@SpringJUnitConfig(classes = { AppConfig.class, DataBaseConfig.class })
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application-test.properties")
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepository accountRepository;
    private static Random random = new Random();

    @BeforeEach
    public void setUp() {
        random = new Random();

        // Clear the database before each test
        customerRepository.deleteAll();
        accountRepository.deleteAll(); // Ensure account repository is cleared as well
    }

    private Customer createTestCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe" + random.nextInt(1000000) + "@example.com");
        customer.setPhone(String.valueOf(random.nextInt(10000)));

        customerRepository.create(customer);
        return customer;
    }

    private void createTestAccountsForCustomer(Customer customer, int count) {
        var accounts = new ArrayList<Account>();
        for (int i = 0; i < count; i++) {
            Account account = new Account();
            account.setAccountNumber("ACC" + random.nextInt(1000000));
            account.setAccountType("SAVINGS");
            account.setBalance(new BigDecimal("1000.00"));
            account.setCurrency("USD");
            account.setIban("US" + random.nextInt(10000000));
            account.setCustomer(customer);
            account.setCreatedAt(LocalDateTime.now());
            accountRepository.create(account);
            accounts.add(account);
        }
        customer.setAccounts(accounts);
    }

    @Test
    public void testCreateCustomer() {
        Customer customer = createTestCustomer();
        assertNotNull(customer.getId());
    }

    @Test
    public void testFindById() {
        Customer customer = createTestCustomer();
        Optional<Customer> foundCustomer = customerRepository.findById(customer.getId());
        assertTrue(foundCustomer.isPresent());
        assertEquals(customer.getId(), foundCustomer.get().getId());
    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = createTestCustomer();
        customer.setLastName("Smith");
        customerRepository.update(customer);

        Optional<Customer> updatedCustomer = customerRepository.findById(customer.getId());
        assertTrue(updatedCustomer.isPresent());
        assertEquals("Smith", updatedCustomer.get().getLastName());
    }

    @Test
    public void testDeleteCustomer() {
        Customer customer = createTestCustomer();

        Optional<Customer> foundCustomerBeforeDeletion = customerRepository.findById(customer.getId());
        assertTrue(foundCustomerBeforeDeletion.isPresent(), "Customer should exist before deletion");

        customerRepository.deleteById(customer.getId());

        Optional<Customer> deletedCustomer = customerRepository.findById(customer.getId());
        assertFalse(deletedCustomer.isPresent(), "Customer should be deleted and not found in the database");
    }

    @Test
    public void testFindAllWithAccountsFetch() {
        Customer customer = createTestCustomer();
        createTestAccountsForCustomer(customer, 2); // Create 2 accounts for the customer
        List<Customer> customers = customerRepository.findAllWithAccountsFetch();
        assertEquals(1, customers.size());
        assertEquals("John", customers.get(0).getFirstName());
        assertEquals(2, customers.get(0).getAccounts().size()); // Check that accounts are fetched
    }

    @Test
    public void testFindAllWithAccountsFetchJPQL() {
        Customer customer = createTestCustomer();
        createTestAccountsForCustomer(customer, 2); // Create 2 accounts for the customer
        List<Customer> customers = customerRepository.findAllWithAccountsFetchJPQL();
        assertEquals(1, customers.size());
        assertEquals("John", customers.get(0).getFirstName());
        assertEquals(2, customers.get(0).getAccounts().size()); // Check that accounts are fetched
    }

    @Test
    public void testFindAllWithAccountsEntityGraph() {
        Customer customer = createTestCustomer();
        createTestAccountsForCustomer(customer, 2); // Create 2 accounts for the customer
        List<Customer> customers = customerRepository.findAllWithAccountsEntityGraph();
        assertEquals(1, customers.size());
        assertEquals("John", customers.get(0).getFirstName());
        assertEquals(2, customers.get(0).getAccounts().size()); // Check that accounts are fetched
    }

}

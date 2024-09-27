package com.alex.homework4example.controller;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.dto.AccountDTO;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.entity.Customer;
import com.alex.homework4example.handlers.GlobalExceptionHandler;
import com.alex.homework4example.repository.AccountRepository;
import com.alex.homework4example.repository.CustomerRepository;
import com.alex.homework4example.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringJUnitConfig(classes = { AppConfig.class, DataBaseConfig.class, GlobalExceptionHandler.class })
@TestPropertySource(locations = "classpath:application-test.properties")
@WebAppConfiguration
@Transactional
class AccountControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private AccountController accountController;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(accountController)
                .setControllerAdvice(globalExceptionHandler)
                .build();

        accountRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @SneakyThrows
    @Test
    void createAccount() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhone("1234567890");
        customerRepository.create(customer);

        MvcResult result = mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"1234567890123456\", \"accountType\":\"SAVINGS\", " +
                                "\"balance\":1000.00, \"currency\":\"USD\", \"iban\":\"US1234567890123456\", " +
                                "\"createdAt\":\"" + LocalDateTime.now() + "\", " +
                                "\"customer\":{\"id\":" + customer.getId() + "}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("1234567890123456"))
                .andReturn();

        var accountDto = objectMapper.readValue(result.getResponse().getContentAsString(), AccountDTO.class);
        Account savedAccount = accountRepository.findById(accountDto.getId()).orElseThrow();
        assertThat(savedAccount.getCustomer().getId()).isEqualTo(customer.getId());
    }

    @SneakyThrows
    @Test
    void getAllAccounts() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhone("1234567890");
        customerRepository.create(customer);

        Account account1 = new Account();
        account1.setAccountNumber("1234567890123456");
        account1.setAccountType("SAVINGS");
        account1.setBalance(new BigDecimal("1000.00"));
        account1.setCurrency("USD");
        account1.setIban("US1234567890123456");
        account1.setCreatedAt(LocalDateTime.now());
        account1.setCustomer(customer);
        accountRepository.create(account1);

        mockMvc.perform(get("/api/v1/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountNumber").value("1234567890123456"));
    }

    @SneakyThrows
    @Test
    void updateAccount() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhone("1234567890");
        customerRepository.create(customer);

        Account account = new Account();
        account.setAccountNumber("1234567890123456");
        account.setAccountType("SAVINGS");
        account.setBalance(new BigDecimal("1000.00"));
        account.setCurrency("USD");
        account.setIban("US1234567890123456");
        account.setCreatedAt(LocalDateTime.now());
        account.setCustomer(customer);
        accountRepository.create(account);

        mockMvc.perform(put("/api/v1/accounts/" + account.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"6543210987654321\", \"accountType\":\"CHECKING\", " +
                                "\"balance\":500.00, \"currency\":\"EUR\", \"iban\":\"EU6543210987654321\", " +
                                "\"customer\":{\"id\":" + customer.getId() + "}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("6543210987654321"));

        Account updatedAccount = accountRepository.findById(account.getId()).orElseThrow();
        assertThat(updatedAccount.getAccountNumber()).isEqualTo("6543210987654321");
    }

    @SneakyThrows
    @Test
    void deleteAccount() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhone("1234567890");
        customerRepository.create(customer);

        Account account = new Account();
        account.setAccountNumber("1234567890123456");
        account.setAccountType("SAVINGS");
        account.setBalance(new BigDecimal("1000.00"));
        account.setCurrency("USD");
        account.setIban("US1234567890123456");
        account.setCreatedAt(LocalDateTime.now());
        account.setCustomer(customer);
        account = accountRepository.create(account);

        mockMvc.perform(delete("/api/v1/accounts/" + account.getId()))
                .andExpect(status().isOk());

        assertThat(accountRepository.findById(account.getId())).isEmpty();
    }

    @SneakyThrows
    @Test
    void getAccountById_NotFound() {
        mockMvc.perform(get("/api/v1/accounts/999"))
                .andExpect(status().isNotFound());
    }

}

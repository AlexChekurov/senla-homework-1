package com.alex.homework4example.controller;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.dto.AccountDTO;
import com.alex.homework4example.dto.CardDTO;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.entity.Card;
import com.alex.homework4example.entity.Customer;
import com.alex.homework4example.handlers.GlobalExceptionHandler;
import com.alex.homework4example.repository.AccountRepository;
import com.alex.homework4example.repository.CardRepository;
import com.alex.homework4example.repository.CustomerRepository;
import com.alex.homework4example.service.CardService;
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
import java.time.LocalDate;
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
class CardControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private CardController cardController;

    @Autowired
    private CardService cardService;

    @Autowired
    private CardRepository cardRepository;

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
        this.mockMvc = MockMvcBuilders.standaloneSetup(cardController)
                .setControllerAdvice(globalExceptionHandler)
                .build();

        cardRepository.deleteAll();
        accountRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @SneakyThrows
    @Test
    void createCard() {
        //given
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

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setAccountType(account.getAccountType());
        accountDTO.setBalance(account.getBalance());
        accountDTO.setCurrency(account.getCurrency());
        accountDTO.setIban(account.getIban());
        accountDTO.setCreatedAt(account.getCreatedAt());

        //when
        MvcResult result = mockMvc.perform(post("/api/v1/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"account\":{\"id\":" + accountDTO.getId() + ", \"accountNumber\":\"" + accountDTO.getAccountNumber() + "\", " +
                                "\"accountType\":\"" + accountDTO.getAccountType() + "\", \"balance\":" + accountDTO.getBalance() + ", " +
                                "\"currency\":\"" + accountDTO.getCurrency() + "\", \"iban\":\"" + accountDTO.getIban() + "\", " +
                                "\"createdAt\":\"" + accountDTO.getCreatedAt() + "\"}, " +
                                "\"customerId\":" + customer.getId() + ", " +
                                "\"cardNumber\":\"1234567890123456\", \"cardType\":\"DEBIT\", " +
                                "\"expirationDate\":\"2025-12-31\", \"cvv\":\"123\", " +
                                "\"createdAt\":\"" + LocalDateTime.now() + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNumber").value("1234567890123456"))
                .andReturn();

        //then
        var cardDto = objectMapper.readValue(result.getResponse().getContentAsString(), CardDTO.class);
        Card savedCard = cardRepository.findById(cardDto.getId()).orElseThrow();
        assertThat(savedCard.getCardNumber()).isEqualTo("1234567890123456");
        assertThat(savedCard.getAccount().getId()).isEqualTo(account.getId());
        assertThat(cardDto.getAccount().getId()).isEqualTo(account.getId());
    }

    @SneakyThrows
    @Test
    void getAllCards() {
        //given
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

        Card card = new Card();
        card.setCardNumber("1234567890123456");
        card.setCardType("DEBIT");
        card.setExpirationDate(LocalDate.of(2025, 12, 31));
        card.setCvv("123");
        card.setCreatedAt(LocalDate.now());
        card.setAccount(account);
        cardRepository.create(card);

        //when, then
        mockMvc.perform(get("/api/v1/cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cardNumber").value("1234567890123456"));
    }

    @SneakyThrows
    @Test
    void updateCard() {
        //given
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

        Card card = new Card();
        card.setCardNumber("1234567890123456");
        card.setCardType("DEBIT");
        card.setExpirationDate(LocalDate.of(2025, 12, 31));
        card.setCvv("123");
        card.setCreatedAt(LocalDate.now());
        card.setAccount(account);
        cardRepository.create(card);

        //when
        mockMvc.perform(put("/api/v1/cards/" + card.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountId\":" + account.getId() + ", \"customerId\":" + customer.getId() + ", " +
                                "\"cardNumber\":\"6543210987654321\", \"cardType\":\"CREDIT\", " +
                                "\"expirationDate\":\"2026-12-31\", \"cvv\":\"321\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNumber").value("6543210987654321"));

        //then
        Card updatedCard = cardRepository.findById(card.getId()).orElseThrow();
        assertThat(updatedCard.getCardNumber()).isEqualTo("6543210987654321");
    }

    @SneakyThrows
    @Test
    void deleteCard() {
        //given
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

        Card card = new Card();
        card.setCardNumber("1234567890123456");
        card.setCardType("DEBIT");
        card.setExpirationDate(LocalDate.of(2025, 12, 31));
        card.setCvv("123");
        card.setCreatedAt(LocalDate.now());
        card.setAccount(account);
        cardRepository.create(card);

        //when
        mockMvc.perform(delete("/api/v1/cards/" + card.getId()))
                .andExpect(status().isOk());

        //then
        assertThat(cardRepository.findById(card.getId())).isEmpty();
    }

    @SneakyThrows
    @Test
    void getCardById_NotFound() {
        //when, then
        mockMvc.perform(get("/api/v1/cards/999"))
                .andExpect(status().isNotFound());
    }

}

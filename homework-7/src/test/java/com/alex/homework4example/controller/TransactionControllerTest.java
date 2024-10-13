package com.alex.homework4example.controller;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.dto.TransactionDTO;
import com.alex.homework4example.entity.Transaction;
import com.alex.homework4example.handlers.GlobalExceptionHandler;
import com.alex.homework4example.repository.TransactionRepository;
import com.alex.homework4example.service.TransactionService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application-test.properties")
class TransactionControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private TransactionController transactionController;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(transactionController)
                .setControllerAdvice(globalExceptionHandler)
                .build();

        transactionRepository.deleteAll();
    }

    @SneakyThrows
    @Test
    void createTransaction() {
        //given
        String transactionJson = """
                {
                    "amount": 1000.00,
                    "transactionDate": "2023-09-26T10:15:30",
                    "sourceAccountId": 1,
                    "destinationAccountId": 2,
                    "currency": "USD"
                }
                """;
        //when
        mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(1000.00))
                .andExpect(jsonPath("$.currency").value("USD"));

        //then
        assertThat(transactionRepository.findAll()).hasSize(1);
        Transaction transaction = transactionRepository.findAll().get(0);
        assertThat(transaction.getAmount()).isEqualByComparingTo(BigDecimal.valueOf(1000.00));
        assertThat(transaction.getCurrency()).isEqualTo("USD");
    }

    @SneakyThrows
    @Test
    void getAllTransactions() {
        //given
        transactionService.create(new TransactionDTO(null, BigDecimal.valueOf(1000.00),
                LocalDateTime.now(), 1L, 2L, "USD"));

        //when
        mockMvc.perform(get("/api/v1/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].amount").value(1000.00));

        //then
        assertThat(transactionRepository.findAll()).hasSize(1);
    }

    @SneakyThrows
    @Test
    void getTransactionById() {
        //given
        TransactionDTO createdTransaction = transactionService.create(new TransactionDTO(null,
                BigDecimal.valueOf(1000.00), LocalDateTime.now(), 1L, 2L, "USD"));

        //when
        mockMvc.perform(get("/api/v1/transactions/" + createdTransaction.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(1000.00));

        //then
        assertThat(transactionRepository.findById(createdTransaction.getId())).isPresent();
    }

    @SneakyThrows
    @Test
    void getTransactionById_NotFound() {
        //when
        mockMvc.perform(get("/api/v1/transactions/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Can't find entity with id: 999"));

        //then
        assertThat(transactionRepository.findAll()).isEmpty();
    }

    @SneakyThrows
    @Test
    void updateTransaction() {
        //given
        TransactionDTO createdTransaction = transactionService.create(new TransactionDTO(null,
                BigDecimal.valueOf(1000.00), LocalDateTime.now(), 1L, 2L, "USD"));
        String updatedTransactionJson = """
                {
                    "amount": 2000.00,
                    "transactionDate": "2023-09-26T11:15:30",
                    "sourceAccountId": 1,
                    "destinationAccountId": 2,
                    "currency": "EUR"
                }
                """;

        //when
        mockMvc.perform(put("/api/v1/transactions/" + createdTransaction.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedTransactionJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(2000.00))
                .andExpect(jsonPath("$.currency").value("EUR"));

        //then
        Transaction updatedTransaction = transactionRepository.findById(createdTransaction.getId()).get();
        assertThat(updatedTransaction.getAmount()).isEqualByComparingTo(BigDecimal.valueOf(2000.00));
        assertThat(updatedTransaction.getCurrency()).isEqualTo("EUR");
    }

    @SneakyThrows
    @Test
    void updateTransaction_NotFound() {
        //given
        String updatedTransactionJson = """
                {
                    "amount": 2000.00,
                    "transactionDate": "2023-09-26T11:15:30",
                    "sourceAccountId": 1,
                    "destinationAccountId": 2,
                    "currency": "EUR"
                }
                """;

        //when
        mockMvc.perform(put("/api/v1/transactions/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedTransactionJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Can't update transaction with id: 999"));

        //then
        assertThat(transactionRepository.findAll()).isEmpty();
    }

    @SneakyThrows
    @Test
    void deleteTransaction() {
        //given
        TransactionDTO createdTransaction = transactionService.create(new TransactionDTO(null,
                BigDecimal.valueOf(1000.00), LocalDateTime.now(), 1L, 2L, "USD"));

        //when
        mockMvc.perform(delete("/api/v1/transactions/" + createdTransaction.getId()))
                .andExpect(status().isOk());

        //then
        assertThat(transactionRepository.findById(createdTransaction.getId())).isNotPresent();
    }
}

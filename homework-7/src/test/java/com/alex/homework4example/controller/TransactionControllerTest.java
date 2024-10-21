package com.alex.homework4example.controller;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.dto.AccountDTO;
import com.alex.homework4example.dto.TransactionDTO;
import com.alex.homework4example.handlers.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.*;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig(classes = { AppConfig.class, DataBaseConfig.class, GlobalExceptionHandler.class })
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(
        scripts = { "classpath:sql/transactions_test_data.sql" },
        config = @SqlConfig(encoding = "UTF-8")
)
@Transactional
class TransactionControllerTest {

    private MockMvc mockMvc;
    private String jwtToken;

    @Autowired
    private TransactionController transactionController;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(transactionController)
                .setControllerAdvice(globalExceptionHandler)
                .build();

        // Получаем JWT-токен
        jwtToken = obtainJwtToken("testUser", "testPassword");
    }

    @SneakyThrows
    private String obtainJwtToken(String username, String password) {
        String content = "{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }";

        MvcResult result = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getHeader("Authorization");
        assertThat(token).isNotNull();

        return token.replace("Bearer ", "");
    }

    @SneakyThrows
    @Test
    void createTransaction() {
        // given
        AccountDTO fromAccount = new AccountDTO();
        fromAccount.setId(505L);
        fromAccount.setAccountNumber("1234567890");
        fromAccount.setAccountType("SAVINGS");
        fromAccount.setBalance(BigDecimal.valueOf(1000.00));
        fromAccount.setCurrency("USD");
        fromAccount.setIban("US12345678901234567890123456");
        fromAccount.setCustomerId(404L);

        AccountDTO toAccount = new AccountDTO();
        toAccount.setId(606L);
        toAccount.setAccountNumber("0987654321");
        toAccount.setAccountType("CHECKING");
        toAccount.setBalance(BigDecimal.valueOf(500.00));
        toAccount.setCurrency("USD");
        toAccount.setIban("US09876543210987654321098765");
        toAccount.setCustomerId(404L);

        TransactionDTO transaction = new TransactionDTO();
        transaction.setAmount(BigDecimal.valueOf(12341.00));
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setCurrency("USD");

        // when
        String transactionJson = mockMvc.perform(post("/api/v1/transactions")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(12341.00))
                .andExpect(jsonPath("$.currency").value("USD"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Integer recordId = JsonPath.read(transactionJson, "$.id");

        // then
        mockMvc.perform(get("/api/v1/transactions/" + recordId)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(12341.00))
                .andExpect(jsonPath("$.currency").value("USD"));
    }

    @SneakyThrows
    @Test
    void getAllTransactions() {
        // when
        String response = mockMvc.perform(get("/api/v1/transactions")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        TransactionDTO[] transactions = objectMapper.readValue(response, TransactionDTO[].class);
        assertThat(transactions).hasSizeGreaterThanOrEqualTo(1)
                .extracting(TransactionDTO::getAmount)
                .contains(BigDecimal.valueOf(200.50).setScale(2, RoundingMode.HALF_UP));
    }

    @SneakyThrows
    @Test
    void getTransactionById() {
        // then
        mockMvc.perform(get("/api/v1/transactions/909")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(300.75))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @SneakyThrows
    @Test
    void getTransactionById_NotFound() {
        // then
        mockMvc.perform(get("/api/v1/transactions/999")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Can't find entity with id: 999"));
    }

    @SneakyThrows
    @Test
    void updateTransaction() {
        // given
        String responseString = mockMvc.perform(get("/api/v1/transactions/808")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currency").value("USD"))
                .andReturn().getResponse().getContentAsString();

        String putRequest = responseString.replaceAll("USD", "RUB");

        // when
        mockMvc.perform(put("/api/v1/transactions/808")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(putRequest))
                .andExpect(status().isOk());

        // then
        mockMvc.perform(get("/api/v1/transactions/808")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currency").value("RUB"));
    }

    @SneakyThrows
    @Test
    void updateTransaction_NotFound() {
        // given
        String updatedTransactionJson = """
                {
                    "amount": 2000.00,
                    "transactionDate": "2023-09-26T11:15:30",
                    "sourceAccountId": 1,
                    "destinationAccountId": 2,
                    "currency": "EUR"
                }
                """;

        // when
        mockMvc.perform(put("/api/v1/transactions/999")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedTransactionJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Can't update transaction with id: 999"));
    }

    @SneakyThrows
    @Test
    void deleteTransaction() {
        // when
        mockMvc.perform(delete("/api/v1/transactions/707")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());

        // then
        mockMvc.perform(get("/api/v1/transactions/707")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Can't find entity with id: 707"));
    }

    @SneakyThrows
    @Test
    void accessWithoutToken_ShouldReturnUnauthorized() {
        mockMvc.perform(get("/api/v1/transactions"))
                .andExpect(status().isUnauthorized());
    }
}

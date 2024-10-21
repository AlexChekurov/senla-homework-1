package com.alex.homework4example.controller;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.dto.AccountDTO;
import com.alex.homework4example.handlers.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig(classes = { AppConfig.class, DataBaseConfig.class, GlobalExceptionHandler.class })
@TestPropertySource(locations = "classpath:application-test.properties")
@WebAppConfiguration
@Transactional
@Sql(
        scripts = { "classpath:sql/accounts_test_data.sql" },
        config = @SqlConfig(encoding = "UTF-8")
)
class AccountControllerTest {

    private MockMvc mockMvc;
    private String jwtToken;

    @Autowired
    private AccountController accountController;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(accountController)
                .setControllerAdvice(globalExceptionHandler)
                .build();

        // Получаем JWT-токен
        jwtToken = obtainJwtToken("testUser", "testPassword");
    }

    // Метод для получения JWT-токена
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
    void createAccount() {
        // when
        MvcResult result = mockMvc.perform(post("/api/v1/accounts")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"1234567890123456\", \"accountType\":\"SAVINGS\", " +
                                 "\"balance\":1000.00, \"currency\":\"USD\", \"iban\":\"US1234567890123456\", " +
                                 "\"createdAt\":\"" + LocalDateTime.now() + "\", " +
                                 "\"customer\":{\"id\": 15 }}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("1234567890123456"))
                .andReturn();

        // then
        var accountDto = objectMapper.readValue(result.getResponse().getContentAsString(), AccountDTO.class);

        assertThat(accountDto.getId()).isNotNull();
        mockMvc.perform(get("/api/v1/accounts/" + accountDto.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("1234567890123456"))
                .andExpect(jsonPath("$.id").value(accountDto.getId()));
    }

    @SneakyThrows
    @Test
    void getAllAccounts() {
        // when
        var responseString = mockMvc.perform(get("/api/v1/accounts")
                        .header("Authorization", "Bearer " + jwtToken)
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .queryParam("accountNumber", "322322344"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        var accountDtoList = objectMapper.readValue(responseString, AccountDTO[].class);
        assertThat(accountDtoList).hasSizeGreaterThanOrEqualTo(1);
    }

    @SneakyThrows
    @Test
    void updateAccount() {
        var expectedIban = "RU12345678901234567890123456";
        // given
        var response = mockMvc.perform(get("/api/v1/accounts/14")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var putRequest = response.replaceAll("US12345678901234567890123456", expectedIban);

        // when
        mockMvc.perform(put("/api/v1/accounts/14")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(putRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.iban").value(expectedIban));

        // then
        mockMvc.perform(get("/api/v1/accounts/14")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.iban").value(expectedIban));
    }

    @SneakyThrows
    @Test
    void deleteAccount() {
        // when
        mockMvc.perform(delete("/api/v1/accounts/17")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());

        // then
        mockMvc.perform(get("/api/v1/accounts/17")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    void getAccountById_NotFound() {
        mockMvc.perform(get("/api/v1/accounts/999")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    void accessWithoutToken_ShouldReturnUnauthorized() {
        mockMvc.perform(get("/api/v1/accounts"))
                .andExpect(status().isUnauthorized());
    }
}

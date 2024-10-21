package com.alex.homework4example.controller;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.dto.CardDTO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig(classes = { AppConfig.class, DataBaseConfig.class, GlobalExceptionHandler.class })
@TestPropertySource(locations = "classpath:application-test.properties")
@WebAppConfiguration
@Transactional
@Sql(
        scripts = { "classpath:sql/card_test_data.sql" },
        config = @SqlConfig(encoding = "UTF-8")
)
class CardControllerTest {

    private MockMvc mockMvc;
    private String jwtToken;

    @Autowired
    private CardController cardController;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(cardController)
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
    void createCard() {
        // given
        var newCardDto = """
                {
                  "cardNumber": "3333-5678-9101-1121",
                  "cardType": "BELTA_TRANS",
                  "expirationDate": "2026-12-29",
                  "cvv": "433",
                  "accountId": 115555
                }
                """;
        // when
        var cardResponse = mockMvc.perform(post("/api/v1/cards")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCardDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNumber").value("3333-5678-9101-1121"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        var cardId = JsonPath.read(cardResponse, "$.id");
        mockMvc.perform(get("/api/v1/cards/" + cardId)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardType").value("BELTA_TRANS"))
                .andExpect(jsonPath("$.cardNumber").value("3333-5678-9101-1121"));
    }

    @SneakyThrows
    @Test
    void getAllCards() {
        // when
        var cardsString = mockMvc.perform(get("/api/v1/cards")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        var cards = objectMapper.readValue(cardsString, CardDTO[].class);
        assertThat(cards).hasSizeGreaterThanOrEqualTo(2)
                .extracting(CardDTO::getCardNumber)
                .contains("4445-5678-9101-1121");
    }

    @SneakyThrows
    @Test
    void updateCard() {
        // given
        var cardDTOString = """
                {
                  "id": 117777,
                  "cardNumber": "4445-5678-9101-1121",
                  "cardType": "PAY_PALL",
                  "expirationDate": "2026-12-31",
                  "cvv": "123",
                  "accountId": 115555
                }
                """;

        // when
        mockMvc.perform(put("/api/v1/cards/117777")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cardDTOString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNumber").value("4445-5678-9101-1121"));

        // then
        mockMvc.perform(get("/api/v1/cards/117777")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardType").value("PAY_PALL"));
    }

    @SneakyThrows
    @Test
    void deleteCard() {
        // when
        mockMvc.perform(delete("/api/v1/cards/118888")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());

        // then
        mockMvc.perform(get("/api/v1/cards/118888")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    void getCardById_NotFound() {
        // when, then
        mockMvc.perform(get("/api/v1/cards/999")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    void accessWithoutToken_ShouldReturnUnauthorized() {
        mockMvc.perform(get("/api/v1/cards"))
                .andExpect(status().isUnauthorized());
    }
}

package com.alex.homework4example.controller;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.dto.UserDTO;
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
        scripts = { "classpath:sql/user_test_data.sql" },
        config = @SqlConfig(encoding = "UTF-8")
)
class UserControllerTest {

    private MockMvc mockMvc;
    private String jwtToken;

    @Autowired
    private UserController userController;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(globalExceptionHandler)
                .build();

        // Получаем JWT-токен с ролью ADMIN
        jwtToken = obtainJwtToken("adminUser", "adminPassword");
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
    void getAllUsers() {
        // when
        var usersListString = mockMvc.perform(get("/api/v1/users")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        var usersList = objectMapper.readValue(usersListString, UserDTO[].class);
        assertThat(usersList).hasSizeGreaterThanOrEqualTo(2)
                .extracting(UserDTO::getId)
                .contains(1425487L);
    }

    @SneakyThrows
    @Test
    void getUserById() {
        // when, then
        mockMvc.perform(get("/api/v1/users/1425487")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("alex_doe"));
    }

    @SneakyThrows
    @Test
    void createUser() {
        // when
        var resultString = mockMvc.perform(post("/api/v1/users")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"username\":\"alex_wwww\", \"password\":\"password\", \"role\":{\"id\": 43678, "
                                + "\"name\":\"Admin\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("alex_wwww"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        var userId = JsonPath.parse(resultString).read("$.id");

        // then
        mockMvc.perform(get("/api/v1/users/" + userId)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("alex_wwww"));
    }

    @SneakyThrows
    @Test
    void updateUser() {
        // given
        var putRequest = """
                {
                  "id" : 1425487,
                  "username" : "neuton",
                  "password" : "qqqq",
                  "roleId" : 76543
                }
                """;
        // when
        mockMvc.perform(put("/api/v1/users/1425487")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(putRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("neuton"));

        // then
        mockMvc.perform(get("/api/v1/users/1425487")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("neuton"));
    }

    @SneakyThrows
    @Test
    void deleteUser() {
        // when
        mockMvc.perform(delete("/api/v1/users/7893")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());

        // then
        mockMvc.perform(get("/api/v1/users/7893")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    void getUserById_NotFound() {
        // when, then
        mockMvc.perform(get("/api/v1/users/999")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    void accessWithoutToken_ShouldReturnUnauthorized() {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isUnauthorized());
    }

    @SneakyThrows
    @Test
    void accessWithInsufficientRole_ShouldReturnForbidden() {
        // Получаем токен пользователя с ролью USER
        String userToken = obtainJwtToken("testUser", "testPassword");

        mockMvc.perform(get("/api/v1/users")
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isForbidden());
    }
}

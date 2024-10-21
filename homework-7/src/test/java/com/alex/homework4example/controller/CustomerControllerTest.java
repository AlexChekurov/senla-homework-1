package com.alex.homework4example.controller;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.dto.CustomerDTO;
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
        scripts = { "classpath:sql/customers_test_data.sql" },
        config = @SqlConfig(encoding = "UTF-8")
)
class CustomerControllerTest {

    private MockMvc mockMvc;
    private String jwtToken;

    @Autowired
    private CustomerController customerController;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(customerController)
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
    void createCustomer() {
        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Walter");
        customerDTO.setLastName("Doessss");
        customerDTO.setEmail("john-d@example1.com");
        customerDTO.setPhone("1234567890");
        customerDTO.setUserId(234653673673734L);

        // when
        var responseString = mockMvc.perform(post("/api/v1/customers")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Walter"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        var customerId = JsonPath.parse(responseString).read("$.id");
        mockMvc.perform(get("/api/v1/customers/" + customerId)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Walter"))
                .andExpect(jsonPath("$.lastName").value("Doessss"));
    }

    @SneakyThrows
    @Test
    void getAllCustomers() {
        // when
        var allCustomers = mockMvc.perform(get("/api/v1/customers")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        var customers = objectMapper.readValue(allCustomers, CustomerDTO[].class);
        assertThat(customers).hasSizeGreaterThanOrEqualTo(1)
                .extracting(CustomerDTO::getId)
                .contains(1511L);
    }

    @SneakyThrows
    @Test
    void updateCustomer() {
        // given
        var customerString = mockMvc.perform(get("/api/v1/customers/1511")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var putRequest = customerString.replace("+1234567890", "-123");

        // when
        mockMvc.perform(put("/api/v1/customers/1511")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(putRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone").value("-123"));

        // then
        mockMvc.perform(get("/api/v1/customers/1511")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone").value("-123"));
    }

    @SneakyThrows
    @Test
    void deleteCustomer() {
        // when
        mockMvc.perform(delete("/api/v1/customers/1415")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());

        // then
        mockMvc.perform(get("/api/v1/customers/1415")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    void getCustomerById_NotFound() {
        // when, then
        mockMvc.perform(get("/api/v1/customers/999")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    void accessWithoutToken_ShouldReturnUnauthorized() {
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isUnauthorized());
    }
}

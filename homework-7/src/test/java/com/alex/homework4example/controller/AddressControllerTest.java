package com.alex.homework4example.controller;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.dto.AddressDTO;
import com.alex.homework4example.handlers.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

@SpringJUnitConfig(classes = {AppConfig.class, DataBaseConfig.class, GlobalExceptionHandler.class})
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(
        scripts = {"classpath:sql/address_test_data.sql"},
        config = @SqlConfig(encoding = "UTF-8")
)
@Transactional
class AddressControllerTest {

    private MockMvc mockMvc;
    private String jwtToken;

    @Autowired
    private AddressController addressController;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(addressController)
                .setControllerAdvice(globalExceptionHandler)
                .build();

        // Obtain JWT token
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
    void createAddress() {
        // Given
        String addressJson = """
                {
                    "street": "Main St",
                    "city": "Moxon",
                    "postalCode": "123r113"
                }
                """;

        // When
        var response = mockMvc.perform(post("/api/v1/addresses")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addressJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street").value("Main St"))
                .andExpect(jsonPath("$.city").value("Moxon"))
                .andExpect(jsonPath("$.postalCode").value("123r113"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        var createdAddress = objectMapper.readValue(response, AddressDTO.class);

        // Then
        mockMvc.perform(get("/api/v1/addresses/" + createdAddress.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street").value("Main St"))
                .andExpect(jsonPath("$.city").value("Moxon"))
                .andExpect(jsonPath("$.postalCode").value("123r113"));
    }

    @SneakyThrows
    @Test
    void getAllAddresses() {
        // When
        var responseString = mockMvc.perform(get("/api/v1/addresses")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var addresses = objectMapper.readValue(responseString, AddressDTO[].class);

        // Then
        assertThat(addresses).hasSizeGreaterThanOrEqualTo(2);
    }

    @SneakyThrows
    @Test
    void getAddressById() {
        // When
        mockMvc.perform(get("/api/v1/addresses/555555")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street").value("123 Main St"))
                .andExpect(jsonPath("$.city").value("New Alabama"))
                .andExpect(jsonPath("$.postalCode").value("10003"));
    }

    @SneakyThrows
    @Test
    void getAddressById_NotFound() {
        // When
        mockMvc.perform(get("/api/v1/addresses/999")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Can't find entity with id: 999"));
    }

    @SneakyThrows
    @Test
    void updateAddress() {
        // Given
        var existingAddress = mockMvc.perform(get("/api/v1/addresses/22222")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var updatedPostalCode = "-1";
        var putRequest = existingAddress.replace("123412311122233", updatedPostalCode);

        // When
        mockMvc.perform(put("/api/v1/addresses/22222")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(putRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postalCode").value(updatedPostalCode));

        // Then
        mockMvc.perform(get("/api/v1/addresses/22222")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postalCode").value(updatedPostalCode));
    }

    @SneakyThrows
    @Test
    void updateAddress_NotFound() {
        // Given
        String updatedAddressJson = """
                {
                    "street": "Elm St",
                    "city": "Los Angeles",
                    "postalCode": "90001"
                }
                """;

        // When
        mockMvc.perform(put("/api/v1/addresses/999")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedAddressJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Can't update address with addressId: 999"));
    }

    @SneakyThrows
    @Test
    void deleteAddress() {
        // When
        mockMvc.perform(delete("/api/v1/addresses/888888")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());

        // Then
        mockMvc.perform(get("/api/v1/addresses/888888")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    void accessWithoutToken_ShouldReturnUnauthorized() {
        mockMvc.perform(get("/api/v1/addresses"))
                .andExpect(status().isUnauthorized());
    }
}

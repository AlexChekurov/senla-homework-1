package com.alex.homework4example.controller;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.dto.AddressDTO;
import com.alex.homework4example.entity.Address;
import com.alex.homework4example.handlers.GlobalExceptionHandler;
import com.alex.homework4example.repository.AddressRepository;
import com.alex.homework4example.service.AddressService;
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
class AddressControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private AddressController addressController;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(addressController)
                .setControllerAdvice(globalExceptionHandler)
                .build();

        addressRepository.deleteAll();
    }

    @SneakyThrows
    @Test
    void createAddress() {
        //given
        String addressJson = """
                {
                    "street": "Main St",
                    "city": "New York",
                    "postalCode": "10001"
                }
                """;

        //when
        mockMvc.perform(post("/api/v1/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addressJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street").value("Main St"))
                .andExpect(jsonPath("$.city").value("New York"))
                .andExpect(jsonPath("$.postalCode").value("10001"));

        //then
        assertThat(addressRepository.findAll()).hasSize(1);
        Address address = addressRepository.findAll().get(0);
        assertThat(address.getStreet()).isEqualTo("Main St");
        assertThat(address.getCity()).isEqualTo("New York");
        assertThat(address.getPostalCode()).isEqualTo("10001");
    }

    @SneakyThrows
    @Test
    void getAllAddresses() {
        //given
        addressService.create(new AddressDTO(null, "Main St", "New York", "10001"));

        //when
        mockMvc.perform(get("/api/v1/addresses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].street").value("Main St"))
                .andExpect(jsonPath("$[0].city").value("New York"))
                .andExpect(jsonPath("$[0].postalCode").value("10001"));

        //then
        assertThat(addressRepository.findAll()).hasSize(1);
    }

    @SneakyThrows
    @Test
    void getAddressById() {
        //given
        AddressDTO createdAddress = addressService.create(new AddressDTO(null, "Main St", "New York", "10001"));

        //when
        mockMvc.perform(get("/api/v1/addresses/" + createdAddress.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street").value("Main St"))
                .andExpect(jsonPath("$.city").value("New York"))
                .andExpect(jsonPath("$.postalCode").value("10001"));

        //then
        assertThat(addressRepository.findById(createdAddress.getId())).isPresent();
    }

    @SneakyThrows
    @Test
    void getAddressById_NotFound() {
        //when
        mockMvc.perform(get("/api/v1/addresses/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Can't find entity with id: 999"));

        //then
        assertThat(addressRepository.findAll()).isEmpty();
    }

    @SneakyThrows
    @Test
    void updateAddress() {
        //given
        AddressDTO createdAddress = addressService.create(new AddressDTO(null, "Main St", "New York", "10001"));
        String updatedAddressJson = """
                {
                    "street": "Elm St",
                    "city": "Los Angeles",
                    "postalCode": "90001"
                }
                """;

        //when
        mockMvc.perform(put("/api/v1/addresses/" + createdAddress.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedAddressJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street").value("Elm St"))
                .andExpect(jsonPath("$.city").value("Los Angeles"))
                .andExpect(jsonPath("$.postalCode").value("90001"));

        //then
        Address updatedAddress = addressRepository.findById(createdAddress.getId()).get();
        assertThat(updatedAddress.getStreet()).isEqualTo("Elm St");
        assertThat(updatedAddress.getCity()).isEqualTo("Los Angeles");
        assertThat(updatedAddress.getPostalCode()).isEqualTo("90001");
    }

    @SneakyThrows
    @Test
    void updateAddress_NotFound() {
        //given
        String updatedAddressJson = """
                {
                    "street": "Elm St",
                    "city": "Los Angeles",
                    "postalCode": "90001"
                }
                """;

        //when
        mockMvc.perform(put("/api/v1/addresses/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedAddressJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Can't update address with addressId: 999"));

        //then
        assertThat(addressRepository.findAll()).isEmpty();
    }

    @SneakyThrows
    @Test
    void deleteAddress() {
        //given
        AddressDTO createdAddress = addressService.create(new AddressDTO(null, "Main St", "New York", "10001"));

        //when
        mockMvc.perform(delete("/api/v1/addresses/" + createdAddress.getId()))
                .andExpect(status().isOk());

        //then
        assertThat(addressRepository.findById(createdAddress.getId())).isNotPresent();
    }
}

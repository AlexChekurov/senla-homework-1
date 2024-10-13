package com.alex.homework4example.controller;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.controller.CustomerController;
import com.alex.homework4example.dto.CustomerDTO;
import com.alex.homework4example.entity.Customer;
import com.alex.homework4example.handlers.GlobalExceptionHandler;
import com.alex.homework4example.repository.CustomerRepository;
import com.alex.homework4example.service.CustomerService;
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
class CustomerControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private CustomerController customerController;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(globalExceptionHandler)
                .build();

        customerRepository.deleteAll();
    }

    @SneakyThrows
    @Test
    void createCustomer() {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Doe");
        customerDTO.setEmail("john.doe@example.com");
        customerDTO.setPhone("1234567890");

        //when
        MvcResult result = mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andReturn();

        //then
        var createdCustomerDto = objectMapper.readValue(result.getResponse().getContentAsString(), CustomerDTO.class);
        Customer savedCustomer = customerRepository.findById(createdCustomerDto.getId()).orElseThrow();
        assertThat(savedCustomer.getFirstName()).isEqualTo("John");
    }

    @SneakyThrows
    @Test
    void getAllCustomers() {
        //given
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhone("1234567890");
        customerRepository.create(customer);

        //when, then
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @SneakyThrows
    @Test
    void updateCustomer() {
        //given
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhone("1234567890");
        customer = customerRepository.create(customer);

        CustomerDTO updateCustomerDTO = new CustomerDTO();
        updateCustomerDTO.setFirstName("Jane");
        updateCustomerDTO.setLastName("Doe");
        updateCustomerDTO.setEmail("jane.doe@example.com");
        updateCustomerDTO.setPhone("0987654321");

        //when
        mockMvc.perform(put("/api/v1/customers/" + customer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCustomerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"));

        //then
        Customer updatedCustomer = customerRepository.findById(customer.getId()).orElseThrow();
        assertThat(updatedCustomer.getFirstName()).isEqualTo("Jane");
    }

    @SneakyThrows
    @Test
    void deleteCustomer() {
        //given
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhone("1234567890");
        customer = customerRepository.create(customer);

        //when
        mockMvc.perform(delete("/api/v1/customers/" + customer.getId()))
                .andExpect(status().isOk());

        //then
        assertThat(customerRepository.findById(customer.getId())).isEmpty();
    }

    @SneakyThrows
    @Test
    void getCustomerById_NotFound() {
        //when, then
        mockMvc.perform(get("/api/v1/customers/999"))
                .andExpect(status().isNotFound());
    }

}

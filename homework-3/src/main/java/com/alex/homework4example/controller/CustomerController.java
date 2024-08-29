package com.alex.homework4example.controller;

import com.alex.homework4example.dto.CustomerDTO;
import com.alex.homework4example.service.impl.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerController {

    private final CustomerService customerService;
    private final ObjectMapper objectMapper;

    @Autowired
    public CustomerController(CustomerService customerService, ObjectMapper objectMapper) {
        this.customerService = customerService;
        this.objectMapper = objectMapper;
    }

    public void initializeAndPerformOperations() throws JsonProcessingException {
        CustomerDTO customer1 = CustomerDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .street("Main Street")
                .city("New York")
                .state("NY")
                .postalCode("10001")
                .country("USA")
                .userId(1)
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .phone("0987654321")
                .street("Second Street")
                .city("Los Angeles")
                .state("CA")
                .postalCode("90001")
                .country("USA")
                .userId(2)
                .build();

        CustomerDTO createdCustomer1 = customerService.create(customer1);
        CustomerDTO createdCustomer2 = customerService.create(customer2);

        System.out.println("Retrieved Customer 1: " + objectMapper.writeValueAsString(customerService.findById(createdCustomer1.getId()).orElseThrow()));

        customerService.delete(createdCustomer2.getId());

        CustomerDTO updatedCustomer1 = CustomerDTO.builder()
                .id(createdCustomer1.getId())
                .firstName("UpdatedJohn")
                .lastName(createdCustomer1.getLastName())
                .email(createdCustomer1.getEmail())
                .phone(createdCustomer1.getPhone())
                .street(createdCustomer1.getStreet())
                .city(createdCustomer1.getCity())
                .state(createdCustomer1.getState())
                .postalCode(createdCustomer1.getPostalCode())
                .country(createdCustomer1.getCountry())
                .userId(createdCustomer1.getUserId())
                .build();
        customerService.update(updatedCustomer1);

        System.out.println("Updated Customer 1: " + objectMapper.writeValueAsString(customerService.findById(updatedCustomer1.getId()).orElseThrow()));
    }
}

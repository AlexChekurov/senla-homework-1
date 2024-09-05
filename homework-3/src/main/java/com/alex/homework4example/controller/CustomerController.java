package com.alex.homework4example.controller;

import com.alex.homework4example.dto.CustomerDTO;
import com.alex.homework4example.entity.Customer;
import com.alex.homework4example.mapper.impl.CustomerMapper;
import com.alex.homework4example.service.impl.CustomerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerController {

    private final CustomerServiceImpl customerService;
    private final CustomerMapper customerMapper;
    private final ObjectMapper objectMapper;

    @Autowired
    public CustomerController(CustomerServiceImpl customerService, CustomerMapper customerMapper, ObjectMapper objectMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
        this.objectMapper = objectMapper;
    }

    public void createCustomers() throws JsonProcessingException {
        CustomerDTO customer1DTO = CustomerDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .street("Main Street")
                .city("New York")
                .state("NY")
                .postalCode("10001")
                .country("USA")
                .userId(1L)
                .build();

        CustomerDTO customer2DTO = CustomerDTO.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .phone("0987654321")
                .street("Second Street")
                .city("Los Angeles")
                .state("CA")
                .postalCode("90001")
                .country("USA")
                .userId(2L)
                .build();

        Customer customer1 = customerMapper.toEntity(customer1DTO);
        Customer customer2 = customerMapper.toEntity(customer2DTO);

        customerService.create(customer1);
        customerService.create(customer2);

        System.out.println("Created Customer 1: " + objectMapper.writeValueAsString(customerMapper.toDto(customer1)));
        System.out.println("Created Customer 2: " + objectMapper.writeValueAsString(customerMapper.toDto(customer2)));
    }

    public void readCustomer(Long id) throws JsonProcessingException {
        Customer customer = customerService.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        CustomerDTO customerDTO = customerMapper.toDto(customer);
        System.out.println("Retrieved Customer: " + objectMapper.writeValueAsString(customerDTO));
    }

    public void updateCustomer(Long id) throws JsonProcessingException {
        Customer customer = customerService.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        CustomerDTO updatedCustomerDTO = CustomerDTO.builder()
                .id(customer.getId())
                .firstName("UpdatedJohn")
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .street(customer.getStreet())
                .city(customer.getCity())
                .state(customer.getState())
                .postalCode(customer.getPostalCode())
                .country(customer.getCountry())
                .userId(customer.getUser().getId())
                .build();

        Customer updatedCustomer = customerMapper.toEntity(updatedCustomerDTO);
        customerService.update(updatedCustomer);

        System.out.println("Updated Customer: " + objectMapper.writeValueAsString(customerMapper.toDto(updatedCustomer)));
    }

    public void deleteCustomer(Long id) {
        customerService.delete(id);
        System.out.println("Deleted Customer with ID: " + id);
    }
}

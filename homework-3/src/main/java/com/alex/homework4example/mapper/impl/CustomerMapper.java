package com.alex.homework4example.mapper.impl;

import com.alex.homework4example.dto.CustomerDTO;
import com.alex.homework4example.entity.Customer;
import com.alex.homework4example.entity.User;
import com.alex.homework4example.dao.jdbc.impl.JdbcUserDao;
import com.alex.homework4example.exception.EntityNotFoundException;
import com.alex.homework4example.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper implements Mapper<Customer, CustomerDTO> {

    private final JdbcUserDao jdbcUserDao;

    public CustomerMapper(JdbcUserDao jdbcUserDao) {
        this.jdbcUserDao = jdbcUserDao;
    }

    @Override
    public CustomerDTO toDto(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .street(customer.getStreet())
                .city(customer.getCity())
                .state(customer.getState())
                .postalCode(customer.getPostalCode())
                .country(customer.getCountry())
                .createdAt(customer.getCreatedAt())
                .userId(customer.getUser().getId())
                .build();
    }

    @Override
    public Customer toEntity(CustomerDTO customerDTO) {
        User user = jdbcUserDao.findById(customerDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + customerDTO.getUserId() + " not found"));

        return Customer.builder()
                .id(customerDTO.getId())
                .firstName(customerDTO.getFirstName())
                .lastName(customerDTO.getLastName())
                .email(customerDTO.getEmail())
                .phone(customerDTO.getPhone())
                .street(customerDTO.getStreet())
                .city(customerDTO.getCity())
                .state(customerDTO.getState())
                .postalCode(customerDTO.getPostalCode())
                .country(customerDTO.getCountry())
                .createdAt(customerDTO.getCreatedAt())
                .user(user)
                .build();
    }
}

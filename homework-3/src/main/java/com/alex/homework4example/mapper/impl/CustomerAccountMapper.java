package com.alex.homework4example.mapper.impl;

import com.alex.homework4example.dto.CustomerAccountDTO;
import com.alex.homework4example.entity.CustomerAccount;
import com.alex.homework4example.entity.Customer;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.dao.jdbc.impl.JdbcCustomerDao;
import com.alex.homework4example.dao.jdbc.impl.JdbcAccountDao;
import com.alex.homework4example.exception.EntityNotFoundException;
import com.alex.homework4example.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerAccountMapper implements Mapper<CustomerAccount, CustomerAccountDTO> {

    private final JdbcCustomerDao jdbcCustomerDao;
    private final JdbcAccountDao jdbcAccountDao;

    public CustomerAccountMapper(JdbcCustomerDao jdbcCustomerDao, JdbcAccountDao jdbcAccountDao) {
        this.jdbcCustomerDao = jdbcCustomerDao;
        this.jdbcAccountDao = jdbcAccountDao;
    }

    @Override
    public CustomerAccountDTO toDto(CustomerAccount customerAccount) {
        return CustomerAccountDTO.builder()
                .id(customerAccount.getId())
                .customerId(customerAccount.getCustomer().getId())
                .accountId(customerAccount.getAccount().getId())
                .build();
    }

    @Override
    public CustomerAccount toEntity(CustomerAccountDTO customerAccountDTO) {
        Customer customer = jdbcCustomerDao.findById(customerAccountDTO.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer with ID " + customerAccountDTO.getCustomerId() + " not found"));

        Account account = jdbcAccountDao.findById(customerAccountDTO.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " + customerAccountDTO.getAccountId() + " not found"));

        return CustomerAccount.builder()
                .id(customerAccountDTO.getId())
                .customer(customer)
                .account(account)
                .build();
    }
}

package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.Dao;
import com.alex.homework4example.dao.jdbc.impl.JdbcCustomerDao;
import com.alex.homework4example.entity.Customer;
import com.alex.homework4example.service.AbstractCrudService;
import com.alex.homework4example.service.CustomerService;
import com.alex.homework4example.utils.ConnectionHolder;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends AbstractCrudService<Customer> implements CustomerService {

    private final JdbcCustomerDao jdbcCustomerDao;

    public CustomerServiceImpl(ConnectionHolder connectionHolder) {
        this.jdbcCustomerDao = new JdbcCustomerDao(connectionHolder);
    }

    @Override
    protected Dao<Long, Customer> getDao() {
        return jdbcCustomerDao;
    }
}

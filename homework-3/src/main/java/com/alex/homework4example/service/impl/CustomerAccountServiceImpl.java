package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.Dao;
import com.alex.homework4example.dao.jdbc.impl.JdbcCustomerAccountDao;
import com.alex.homework4example.entity.CustomerAccount;
import com.alex.homework4example.service.AbstractCrudService;
import com.alex.homework4example.service.CustomerAccountService;
import com.alex.homework4example.utils.ConnectionHolder;
import org.springframework.stereotype.Service;

@Service
public class CustomerAccountServiceImpl extends AbstractCrudService<CustomerAccount> implements CustomerAccountService {

    private final JdbcCustomerAccountDao jdbcCustomerAccountDao;

    public CustomerAccountServiceImpl(ConnectionHolder connectionHolder) {
        this.jdbcCustomerAccountDao = new JdbcCustomerAccountDao(connectionHolder);
    }

    @Override
    protected Dao<Long, CustomerAccount> getDao() {
        return jdbcCustomerAccountDao;
    }
}

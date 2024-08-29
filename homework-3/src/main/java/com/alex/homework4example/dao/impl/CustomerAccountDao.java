package com.alex.homework4example.dao.impl;

import com.alex.homework4example.dao.AbstractDao;
import com.alex.homework4example.entity.CustomerAccount;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CustomerAccountDao extends AbstractDao<Integer, CustomerAccount> {

    private final static AtomicInteger idCounter = new AtomicInteger(0);

    @Override
    protected Integer generateId() {
        return idCounter.incrementAndGet();
    }

    @Override
    protected Integer getId(CustomerAccount customerAccount) {
        return customerAccount.getId();
    }

    @Override
    protected void setId(CustomerAccount customerAccount, Integer id) {
        customerAccount.setId(id);
    }
}

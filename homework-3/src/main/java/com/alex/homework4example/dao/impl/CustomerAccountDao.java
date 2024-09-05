package com.alex.homework4example.dao.impl;

import com.alex.homework4example.dao.AbstractDao;
import com.alex.homework4example.entity.CustomerAccount;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class CustomerAccountDao extends AbstractDao<Long, CustomerAccount> {

    private final static AtomicLong idCounter = new AtomicLong(0);

    @Override
    protected Long generateId() {
        return idCounter.incrementAndGet();
    }

    @Override
    protected Long getId(CustomerAccount customerAccount) {
        return customerAccount.getId() != null ? customerAccount.getId() : null;
    }

    @Override
    protected void setId(CustomerAccount customerAccount, Long id) {
        customerAccount.setId(id);
    }
}

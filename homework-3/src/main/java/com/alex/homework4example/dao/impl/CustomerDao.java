package com.alex.homework4example.dao.impl;

import com.alex.homework4example.dao.AbstractDao;
import com.alex.homework4example.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CustomerDao extends AbstractDao<Integer, Customer> {

    private final static AtomicInteger idCounter = new AtomicInteger(0);

    @Override
    protected Integer generateId() {
        return idCounter.incrementAndGet();
    }

    @Override
    protected Integer getId(Customer customer) {
        return customer.getId();
    }

    @Override
    protected void setId(Customer customer, Integer id) {
        customer.setId(id);
    }
}

package com.alex.homework4example.dao.impl;

import com.alex.homework4example.dao.AbstractDao;
import com.alex.homework4example.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class CustomerDao extends AbstractDao<Long, Customer> {

    private final static AtomicLong idCounter = new AtomicLong(0);

    @Override
    protected Long generateId() {
        return idCounter.incrementAndGet();
    }

    @Override
    protected Long getId(Customer customer) {
        return customer.getId() != null ? customer.getId() : null;
    }

    @Override
    protected void setId(Customer customer, Long id) {
        customer.setId(id);
    }
}

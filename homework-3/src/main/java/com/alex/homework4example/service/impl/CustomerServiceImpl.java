package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.impl.CustomerDao;
import com.alex.homework4example.entity.Customer;
import com.alex.homework4example.service.CustomerService;
import com.alex.homework4example.service.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends AbstractCrudService<Customer> implements CustomerService {

    private final CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    protected CustomerDao getDao() {
        return customerDao;
    }

    @Override
    protected Long getEntityId(Customer customer) {
        return customer.getId() != null ? customer.getId() : null;
    }
}

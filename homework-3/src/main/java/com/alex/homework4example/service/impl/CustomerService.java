package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.impl.CustomerDao;
import com.alex.homework4example.dto.CustomerDTO;
import com.alex.homework4example.entity.Customer;
import com.alex.homework4example.mapper.impl.CustomerMapper;
import com.alex.homework4example.service.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends AbstractCrudService<Customer, CustomerDTO, Integer> {

    private final CustomerDao customerDao;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerDao customerDao, CustomerMapper customerMapper) {
        this.customerDao = customerDao;
        this.customerMapper = customerMapper;
    }

    @Override
    protected CustomerDao getDao() {
        return customerDao;
    }

    @Override
    protected CustomerMapper getMapper() {
        return customerMapper;
    }

    @Override
    protected Integer getEntityId(Customer customer) {
        return customer.getId();
    }
}

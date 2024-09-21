package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.AbstractDao;
import com.alex.homework4example.entity.Customer;
import com.alex.homework4example.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends AbstractCrudService<Customer> implements CustomerService {

    public CustomerServiceImpl(AbstractDao<Customer> dao) {
        super(dao);
    }
}

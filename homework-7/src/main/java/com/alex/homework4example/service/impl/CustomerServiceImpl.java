package com.alex.homework4example.service.impl;

import com.alex.homework4example.dto.CustomerDTO;
import com.alex.homework4example.entity.Customer;
import com.alex.homework4example.mapper.CommonMapper;
import com.alex.homework4example.repository.AbstractRepository;
import com.alex.homework4example.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends AbstractCrudService<Customer, CustomerDTO> implements CustomerService {

    public CustomerServiceImpl(AbstractRepository<Customer> repository, CommonMapper<Customer, CustomerDTO> mapper) {
        super(repository, mapper);
    }
}

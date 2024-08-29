package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.impl.CustomerAccountDao;
import com.alex.homework4example.dto.CustomerAccountDTO;
import com.alex.homework4example.entity.CustomerAccount;
import com.alex.homework4example.mapper.impl.CustomerAccountMapper;
import com.alex.homework4example.service.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class CustomerAccountService extends AbstractCrudService<CustomerAccount, CustomerAccountDTO, Integer> {

    private final CustomerAccountDao customerAccountDao;
    private final CustomerAccountMapper customerAccountMapper;

    public CustomerAccountService(CustomerAccountDao customerAccountDao, CustomerAccountMapper customerAccountMapper) {
        this.customerAccountDao = customerAccountDao;
        this.customerAccountMapper = customerAccountMapper;
    }

    @Override
    protected CustomerAccountDao getDao() {
        return customerAccountDao;
    }

    @Override
    protected CustomerAccountMapper getMapper() {
        return customerAccountMapper;
    }

    @Override
    protected Integer getEntityId(CustomerAccount customerAccount) {
        return customerAccount.getId();
    }
}

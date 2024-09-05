package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.impl.CustomerAccountDao;
import com.alex.homework4example.entity.CustomerAccount;
import com.alex.homework4example.service.CustomerAccountService;
import com.alex.homework4example.service.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class CustomerAccountServiceImpl extends AbstractCrudService<CustomerAccount> implements CustomerAccountService {

    private final CustomerAccountDao customerAccountDao;

    public CustomerAccountServiceImpl(CustomerAccountDao customerAccountDao) {
        this.customerAccountDao = customerAccountDao;
    }

    @Override
    protected CustomerAccountDao getDao() {
        return customerAccountDao;
    }

    @Override
    protected Long getEntityId(CustomerAccount customerAccount) {
        return customerAccount.getId() != null ? customerAccount.getId() : null;
    }
}

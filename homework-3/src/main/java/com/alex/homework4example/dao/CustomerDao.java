package com.alex.homework4example.dao;

import com.alex.homework4example.entity.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao extends AbstractDao<Customer> {

    public CustomerDao() {
        super(Customer.class);
    }

}

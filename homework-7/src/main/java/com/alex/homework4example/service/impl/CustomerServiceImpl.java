package com.alex.homework4example.service.impl;

import com.alex.homework4example.dto.CustomerDTO;
import com.alex.homework4example.entity.Customer;
import com.alex.homework4example.exception.EntityNotFoundException;
import com.alex.homework4example.mapper.CommonMapper;
import com.alex.homework4example.repository.AbstractRepository;
import com.alex.homework4example.service.CustomerService;
import org.springframework.stereotype.Service;


@Service
public class CustomerServiceImpl extends AbstractCrudService<Customer, CustomerDTO> implements CustomerService {

    public CustomerServiceImpl(AbstractRepository<Customer> repository, CommonMapper<Customer, CustomerDTO> mapper) {
        super(repository, mapper);
    }

    @Override
    public CustomerDTO update(Long id, CustomerDTO customerDetails) {
        return findById(id)
                .map(customer -> {
                    customer.setFirstName(customerDetails.getFirstName());
                    customer.setLastName(customerDetails.getLastName());
                    customer.setEmail(customerDetails.getEmail());
                    customer.setPhone(customerDetails.getPhone());
                    return updateEntityToDto(customer);
                })
                .orElseThrow(() -> new EntityNotFoundException("Can't update customer with id: " + id));
    }

}

package com.alex.homework4example.controller;

import com.alex.homework4example.dto.CustomerDTO;
import com.alex.homework4example.exception.EntityNotFoundException;
import com.alex.homework4example.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class CustomerController {

    private final CustomerService customerService;

    public List<CustomerDTO> getAllCustomers() {
        return customerService.findAll();
    }

    public CustomerDTO getCustomerById(Long id) {
        return customerService.findDtoById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find customer with id: " + id));
    }

    public CustomerDTO createCustomer(CustomerDTO customer) {
        return customerService.create(customer);
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDetails) {
        return customerService.findById(id)
                .map(customer -> {
                    customer.setFirstName(customerDetails.getFirstName());
                    customer.setLastName(customerDetails.getLastName());
                    customer.setEmail(customerDetails.getEmail());
                    customer.setPhone(customerDetails.getPhone());
                    return customerService.updateEntityToDto(customer);
                })
                .orElseThrow(() -> new EntityNotFoundException("Can't update customer with id: " + id));
    }

    public void deleteCustomer(Long id) {
        customerService.deleteById(id);
    }
}

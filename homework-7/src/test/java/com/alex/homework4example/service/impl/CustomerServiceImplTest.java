package com.alex.homework4example.service.impl;

import com.alex.homework4example.dto.CustomerDTO;
import com.alex.homework4example.entity.Customer;
import com.alex.homework4example.mapper.CommonMapper;
import com.alex.homework4example.repository.AbstractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private AbstractRepository<Customer> customerRepository;

    @Mock
    private CommonMapper<Customer, CustomerDTO> customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private CustomerDTO customerDTO;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhone("1234567890");

        customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Doe");
        customerDTO.setEmail("john.doe@example.com");
        customerDTO.setPhone("1234567890");
    }

    @Test
    void testCreateCustomer() {
        //given
        when(customerMapper.toEntity(any(CustomerDTO.class))).thenReturn(customer);
        when(customerRepository.create(any(Customer.class))).thenReturn(customer);
        when(customerMapper.toDto(any(Customer.class))).thenReturn(customerDTO);

        //when
        CustomerDTO createdCustomerDTO = customerService.create(customerDTO);

        //then
        assertNotNull(createdCustomerDTO);
        assertEquals(customerDTO.getFirstName(), createdCustomerDTO.getFirstName());
        verify(customerMapper).toEntity(customerDTO);
        verify(customerRepository).create(customer);
        verify(customerMapper).toDto(customer);
    }

    @Test
    void testFindById() {
        //given
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerMapper.toDto(customer)).thenReturn(customerDTO);

        //when
        var foundCustomerDTO = customerService.findDtoById(1L);

        //then
        assertEquals(customerDTO.getId(), foundCustomerDTO.getId());
        verify(customerRepository).findById(1L);
    }

    @Test
    void testUpdateCustomer() {
        //given
        when(customerRepository.findById(customer.getId()))
                .thenReturn(Optional.ofNullable(customer));
        when(customerRepository.update(any(Customer.class))).thenReturn(customer);
        when(customerMapper.toDto(any(Customer.class))).thenReturn(customerDTO);

        //when
        var updatedCustomerDTO = customerService.update(customer.getId(), customerDTO);

        //then
        assertNotNull(updatedCustomerDTO);
        assertEquals(customerDTO.getFirstName(), updatedCustomerDTO.getFirstName());
        verify(customerRepository).update(customer);
        verify(customerMapper).toDto(customer);
    }

    @Test
    void testDeleteById() {
        //given
        when(customerRepository.deleteById(1L)).thenReturn(true);

        //when
        boolean result = customerService.deleteById(1L);

        //then
        assertTrue(result);
        verify(customerRepository).deleteById(1L);
    }

    @Test
    void testFindAll() {
        //given
        when(customerRepository.findAll()).thenReturn(List.of(customer));
        when(customerMapper.toDto(any(Customer.class))).thenReturn(customerDTO);

        //when
        List<CustomerDTO> allCustomers = customerService.findAll();

        //then
        assertEquals(1, allCustomers.size());
        assertEquals(customerDTO.getFirstName(), allCustomers.get(0).getFirstName());
        verify(customerRepository).findAll();
    }

}

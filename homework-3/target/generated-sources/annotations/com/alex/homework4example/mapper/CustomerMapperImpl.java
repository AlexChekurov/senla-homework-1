package com.alex.homework4example.mapper;

import com.alex.homework4example.dto.CustomerDTO;
import com.alex.homework4example.entity.Customer;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-21T21:45:10+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 20.0.2.1 (Amazon.com Inc.)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public Customer toEntity(CustomerDTO accountDTO) {
        if ( accountDTO == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.id( accountDTO.getId() );
        customer.firstName( accountDTO.getFirstName() );
        customer.lastName( accountDTO.getLastName() );
        customer.email( accountDTO.getEmail() );
        customer.phone( accountDTO.getPhone() );
        customer.street( accountDTO.getStreet() );
        customer.city( accountDTO.getCity() );
        customer.state( accountDTO.getState() );
        customer.postalCode( accountDTO.getPostalCode() );
        customer.country( accountDTO.getCountry() );
        customer.createdAt( accountDTO.getCreatedAt() );

        return customer.build();
    }

    @Override
    public CustomerDTO toDto(Customer account) {
        if ( account == null ) {
            return null;
        }

        CustomerDTO.CustomerDTOBuilder customerDTO = CustomerDTO.builder();

        customerDTO.id( account.getId() );
        customerDTO.firstName( account.getFirstName() );
        customerDTO.lastName( account.getLastName() );
        customerDTO.email( account.getEmail() );
        customerDTO.phone( account.getPhone() );
        customerDTO.street( account.getStreet() );
        customerDTO.city( account.getCity() );
        customerDTO.state( account.getState() );
        customerDTO.postalCode( account.getPostalCode() );
        customerDTO.country( account.getCountry() );
        customerDTO.createdAt( account.getCreatedAt() );

        return customerDTO.build();
    }
}

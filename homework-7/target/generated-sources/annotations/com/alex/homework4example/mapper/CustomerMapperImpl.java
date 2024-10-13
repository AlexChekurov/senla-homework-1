package com.alex.homework4example.mapper;

import com.alex.homework4example.dto.CustomerDTO;
import com.alex.homework4example.entity.Customer;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-09T09:54:20+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public Customer toEntity(CustomerDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.id( dto.getId() );
        customer.firstName( dto.getFirstName() );
        customer.lastName( dto.getLastName() );
        customer.email( dto.getEmail() );
        customer.phone( dto.getPhone() );

        return customer.build();
    }

    @Override
    public CustomerDTO toDto(Customer entity) {
        if ( entity == null ) {
            return null;
        }

        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setId( entity.getId() );
        customerDTO.setFirstName( entity.getFirstName() );
        customerDTO.setLastName( entity.getLastName() );
        customerDTO.setEmail( entity.getEmail() );
        customerDTO.setPhone( entity.getPhone() );

        return customerDTO;
    }
}

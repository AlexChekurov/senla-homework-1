package com.alex.homework4example.mapper;

import com.alex.homework4example.dto.AddressDTO;
import com.alex.homework4example.entity.Address;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-21T21:24:34+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public Address toEntity(AddressDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Address address = new Address();

        address.setId( dto.getId() );
        address.setStreet( dto.getStreet() );
        address.setCity( dto.getCity() );
        address.setPostalCode( dto.getPostalCode() );

        return address;
    }

    @Override
    public AddressDTO toDto(Address entity) {
        if ( entity == null ) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setId( entity.getId() );
        addressDTO.setStreet( entity.getStreet() );
        addressDTO.setCity( entity.getCity() );
        addressDTO.setPostalCode( entity.getPostalCode() );

        return addressDTO;
    }
}

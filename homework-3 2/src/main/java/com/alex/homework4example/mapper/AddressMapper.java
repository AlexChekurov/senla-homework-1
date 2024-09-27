package com.alex.homework4example.mapper;

import com.alex.homework4example.dto.AddressDTO;
import com.alex.homework4example.entity.Address;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AddressMapper extends CommonMapper<Address, AddressDTO> {

}

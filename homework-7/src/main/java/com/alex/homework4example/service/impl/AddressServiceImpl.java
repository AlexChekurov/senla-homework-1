package com.alex.homework4example.service.impl;

import com.alex.homework4example.dto.AddressDTO;
import com.alex.homework4example.entity.Address;
import com.alex.homework4example.mapper.CommonMapper;
import com.alex.homework4example.repository.AbstractRepository;
import com.alex.homework4example.service.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends AbstractCrudService<Address, AddressDTO> implements AddressService {
    public AddressServiceImpl(AbstractRepository<Address> repository, CommonMapper<Address, AddressDTO> mapper) {
        super(repository, mapper);
    }
}

package com.alex.homework4example.controller;

import com.alex.homework4example.dto.AddressDTO;
import com.alex.homework4example.exception.EntityNotFoundException;
import com.alex.homework4example.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class AddressController {

    private final AddressService addressService;


    public List<AddressDTO> getAllAddresses() {
        return addressService.findAll();
    }

    public AddressDTO getAddressById(Long id) {
        return addressService.findDtoById(id);
    }

    public AddressDTO createAddress(AddressDTO address) {
        return addressService.create(address);
    }

    public AddressDTO updateAddress(Long id, AddressDTO addressDetails) {
        return addressService.update(id, addressDetails);
    }

    public void deleteAddress(Long id) {
        addressService.deleteById(id);
    }
}

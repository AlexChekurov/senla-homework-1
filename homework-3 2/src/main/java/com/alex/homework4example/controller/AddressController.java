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
        return addressService.findDtoById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find address with id: " + id));
    }

    public AddressDTO createAddress(AddressDTO address) {
        return addressService.create(address);
    }

    public AddressDTO updateAddress(Long id, AddressDTO addressDetails) {
        return addressService.findById(id)
                .map(address -> {
                    address.setStreet(addressDetails.getStreet());
                    address.setCity(addressDetails.getCity());
                    address.setPostalCode(addressDetails.getPostalCode());
                    return addressService.updateEntityToDto(address);
                })
                .orElseThrow(() -> new EntityNotFoundException("Can't update address with id: " + id));
    }

    public void deleteAddress(Long id) {
        addressService.deleteById(id);
    }
}

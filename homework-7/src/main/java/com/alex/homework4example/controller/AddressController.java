package com.alex.homework4example.controller;

import com.alex.homework4example.dto.AddressDTO;
import com.alex.homework4example.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public List<AddressDTO> getAllAddresses() {
        return addressService.findAll();
    }

    @GetMapping("/{addressId}")
    public AddressDTO getAddressById(@PathVariable("addressId") Long id) {
        return addressService.findDtoById(id);
    }

    @PostMapping
    public AddressDTO createAddress(@RequestBody AddressDTO address) {
        return addressService.create(address);
    }

    @PutMapping("/{addressId}")
    public AddressDTO updateAddress(@PathVariable("addressId") Long addressId, @RequestBody AddressDTO addressDetails) {
        return addressService.update(addressId, addressDetails);
    }

    @DeleteMapping("/{addressId}")
    public void deleteAddress(@PathVariable("addressId") Long id) {
        addressService.deleteById(id);
    }

}

package com.alex.homework4example.service.impl;

import com.alex.homework4example.dto.AddressDTO;
import com.alex.homework4example.entity.Address;
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
public class AddressServiceImplTest {

    @Mock
    private AbstractRepository<Address> addressRepository;

    @Mock
    private CommonMapper<Address, AddressDTO> addressMapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Address address;
    private AddressDTO addressDTO;

    @BeforeEach
    public void setUp() {

        address = new Address();
        address.setId(1L);
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setPostalCode("12345");

        addressDTO = new AddressDTO();
        addressDTO.setId(1L);
        addressDTO.setStreet("123 Main St");
        addressDTO.setCity("Anytown");
        addressDTO.setPostalCode("12345");
    }

    @Test
    public void testCreateAddress() {
        when(addressMapper.toEntity(any(AddressDTO.class))).thenReturn(address);
        when(addressRepository.create(any(Address.class))).thenReturn(address);
        when(addressMapper.toDto(any(Address.class))).thenReturn(addressDTO);

        AddressDTO createdAddressDTO = addressService.create(addressDTO);

        assertNotNull(createdAddressDTO);
        assertEquals(addressDTO.getStreet(), createdAddressDTO.getStreet());
        verify(addressMapper).toEntity(addressDTO);
        verify(addressRepository).create(address);
        verify(addressMapper).toDto(address);
    }

    @Test
    public void testFindById() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(addressMapper.toDto(address)).thenReturn(addressDTO);

        Optional<AddressDTO> foundAddressDTO = addressService.findDtoById(1L);

        assertTrue(foundAddressDTO.isPresent());
        assertEquals(addressDTO.getId(), foundAddressDTO.get().getId());
        verify(addressRepository).findById(1L);
    }

    @Test
    public void testUpdateAddress() {
        when(addressMapper.toEntity(any(AddressDTO.class))).thenReturn(address);
        when(addressRepository.update(any(Address.class))).thenReturn(address);
        when(addressMapper.toDto(any(Address.class))).thenReturn(addressDTO);

        AddressDTO updatedAddressDTO = addressService.update(addressDTO);

        assertNotNull(updatedAddressDTO);
        assertEquals(addressDTO.getStreet(), updatedAddressDTO.getStreet());
        verify(addressMapper).toEntity(addressDTO);
        verify(addressRepository).update(address);
        verify(addressMapper).toDto(address);
    }

    @Test
    public void testDeleteById() {
        when(addressRepository.deleteById(1L)).thenReturn(true);

        boolean result = addressService.deleteById(1L);

        assertTrue(result);
        verify(addressRepository).deleteById(1L);
    }

    @Test
    public void testFindAll() {
        when(addressRepository.findAll()).thenReturn(List.of(address));
        when(addressMapper.toDto(any(Address.class))).thenReturn(addressDTO);

        List<AddressDTO> allAddresses = addressService.findAll();

        assertEquals(1, allAddresses.size());
        assertEquals(addressDTO.getStreet(), allAddresses.get(0).getStreet());
        verify(addressRepository).findAll();
    }

}
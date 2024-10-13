package com.alex.homework4example.repository;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.entity.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Transactional
@Rollback
@SpringJUnitConfig(classes = { AppConfig.class, DataBaseConfig.class })
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application-test.properties")
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;
    private Random random;

    @BeforeEach
    void setUp() {
        random = new Random();
        // Clear the database before each test
        addressRepository.deleteAll();
    }

    @Test
    void testCreateAddress() {
        // when
        Address address = createTestAddress();

        // then
        assertNotNull(address.getId());
    }

    @Test
    void testFindById() {
        // given
        Address address = createTestAddress();

        // when
        Optional<Address> foundAddress = addressRepository.findById(address.getId());

        // then
        assertTrue(foundAddress.isPresent());
        assertEquals(address.getId(), foundAddress.get().getId());
    }

    @Test
    void testUpdateAddress() {
        // given
        Address address = createTestAddress();
        address.setCity("Updated City");

        // when
        addressRepository.update(address);

        // then
        Optional<Address> updatedAddress = addressRepository.findById(address.getId());
        assertTrue(updatedAddress.isPresent());
        assertEquals("Updated City", updatedAddress.get().getCity());
    }

    @Test
    void testDeleteAddress() {
        // given
        Address address = createTestAddress();
        Optional<Address> foundAddressBeforeDeletion = addressRepository.findById(address.getId());
        assertTrue(foundAddressBeforeDeletion.isPresent(), "Address should exist before deletion");

        // when
        addressRepository.deleteById(address.getId());

        // then
        Optional<Address> deletedAddress = addressRepository.findById(address.getId());
        assertFalse(deletedAddress.isPresent(), "Address should be deleted and not found in the database");
    }

    @Test
    void testFindAllAddresses() {
        // given
        createTestAddress();
        createTestAddress();

        // when
        List<Address> addresses = addressRepository.findAll();

        // then
        assertEquals(2, addresses.size());
    }

    private Address createTestAddress() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Sample City");
        address.setPostalCode("Sample State");

        addressRepository.create(address);
        return address;
    }

}

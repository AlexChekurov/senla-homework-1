package com.alex.homework4example.repository;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.entity.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
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
@SpringJUnitConfig(classes = {AppConfig.class, DataBaseConfig.class})
@TestPropertySource(locations = "classpath:application-test.properties")
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;
    private Random random;

    @BeforeEach
    public void setUp() {
        random = new Random();

        // Clear the database before each test
        addressRepository.deleteAll();
    }

    private Address createTestAddress() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Sample City");
        address.setPostalCode("Sample State");

        addressRepository.create(address);
        return address;
    }

    @Test
    public void testCreateAddress() {
        Address address = createTestAddress();
        assertNotNull(address.getId());
    }

    @Test
    public void testFindById() {
        Address address = createTestAddress();
        Optional<Address> foundAddress = addressRepository.findById(address.getId());
        assertTrue(foundAddress.isPresent());
        assertEquals(address.getId(), foundAddress.get().getId());
    }

    @Test
    public void testUpdateAddress() {
        Address address = createTestAddress();
        address.setCity("Updated City");
        addressRepository.update(address);

        Optional<Address> updatedAddress = addressRepository.findById(address.getId());
        assertTrue(updatedAddress.isPresent());
        assertEquals("Updated City", updatedAddress.get().getCity());
    }

    @Test
    public void testDeleteAddress() {
        Address address = createTestAddress();

        Optional<Address> foundAddressBeforeDeletion = addressRepository.findById(address.getId());
        assertTrue(foundAddressBeforeDeletion.isPresent(), "Address should exist before deletion");

        addressRepository.deleteById(address.getId());

        Optional<Address> deletedAddress = addressRepository.findById(address.getId());
        assertFalse(deletedAddress.isPresent(), "Address should be deleted and not found in the database");
    }

    @Test
    public void testFindAllAddresses() {
        createTestAddress(); // Create one address
        createTestAddress(); // Create another address
        List<Address> addresses = addressRepository.findAll(); // Assuming there's a findAll() method
        assertEquals(2, addresses.size());
    }
}

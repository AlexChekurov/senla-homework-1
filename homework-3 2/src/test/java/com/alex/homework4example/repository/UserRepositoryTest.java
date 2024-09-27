package com.alex.homework4example.repository;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.entity.Role;
import com.alex.homework4example.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Transactional
@Rollback
@SpringJUnitConfig(classes = { AppConfig.class, DataBaseConfig.class })
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    private Role createTestRole() {
        Role role = new Role();
        role.setName("ROLE_" + UUID.randomUUID().toString());
        roleRepository.create(role);
        return role;
    }

    private User createTestUser() {
        Role role = createTestRole();
        User user = new User();
        user.setUsername(UUID.randomUUID().toString());
        user.setRole(role);
        user.setPassword(UUID.randomUUID().toString());
        userRepository.create(user);
        return user;
    }

    @Test
    public void testCreateUser() {
        User user = createTestUser();
        assertNotNull(user.getId(), "User ID should not be null after creation");
    }

    @Test
    public void testFindById() {
        User user = createTestUser();
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertTrue(foundUser.isPresent(), "User should be found");
        assertEquals(user.getId(), foundUser.get().getId(), "The IDs should match");
    }

    @Test
    public void testUpdateUser() {
        User user = createTestUser();
        user.setUsername("UpdatedUsername");
        userRepository.update(user);

        Optional<User> updatedUser = userRepository.findById(user.getId());
        assertTrue(updatedUser.isPresent(), "User should be found after update");
        assertEquals("UpdatedUsername", updatedUser.get().getUsername(), "Username should be updated");
    }

    @Test
    public void testDeleteUser() {
        User user = createTestUser();

        Optional<User> foundUserBeforeDeletion = userRepository.findById(user.getId());
        assertTrue(foundUserBeforeDeletion.isPresent(), "User should exist before deletion");

        userRepository.deleteById(user.getId());

        Optional<User> deletedUser = userRepository.findById(user.getId());
        assertFalse(deletedUser.isPresent(), "User should be deleted and not found in the database");
    }

    @Test
    public void testFindAllUsers() {
        createTestUser();
        createTestUser();
        List<User> users = userRepository.findAll();
        assertEquals(2, users.size(), "There should be two users in the database");
    }

}

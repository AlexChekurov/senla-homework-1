package com.alex.homework4example.repository;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.entity.Role;
import com.alex.homework4example.entity.User; // Предположим, что у вас есть сущность User
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@Rollback
@SpringJUnitConfig(classes = {AppConfig.class, DataBaseConfig.class})
@TestPropertySource(locations = "classpath:application-test.properties")
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

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

    private User createTestUser(Role role) {
        User user = new User();
        user.setUsername(UUID.randomUUID().toString());
        user.setRole(role);
        user.setPassword(UUID.randomUUID().toString());
        userRepository.create(user);
        return user;
    }

    @Test
    public void testCreateRole() {
        Role role = createTestRole();
        assertNotNull(role.getId(), "Role ID should not be null after creation");
    }

    @Test
    public void testFindById() {
        Role role = createTestRole();
        Optional<Role> foundRole = roleRepository.findById(role.getId());
        assertTrue(foundRole.isPresent(), "Role should be found");
        assertEquals(role.getId(), foundRole.get().getId(), "The IDs should match");
    }

    @Test
    public void testUpdateRole() {
        Role role = createTestRole();
        role.setName("ROLE_UPDATED");
        roleRepository.update(role);

        Optional<Role> updatedRole = roleRepository.findById(role.getId());
        assertTrue(updatedRole.isPresent(), "Role should be found after update");
        assertEquals("ROLE_UPDATED", updatedRole.get().getName(), "Role name should be updated");
    }

    @Test
    public void testFindAllRoles() {
        createTestRole();
        createTestRole();
        List<Role> roles = roleRepository.findAll();
        assertEquals(2, roles.size(), "There should be two roles in the database");
    }
}

package com.alex.homework4example.repository;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.entity.Role;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Transactional
@Rollback
@SpringJUnitConfig(classes = { AppConfig.class, DataBaseConfig.class })
@TestPropertySource(locations = "classpath:application-test.properties")
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();

    }

    @Test
    void testCreateRole() {
        // when
        Role role = createTestRole();

        // then
        assertNotNull(role.getId(), "Role ID should not be null after creation");
    }

    @Test
    void testFindById() {
        // given
        Role role = createTestRole();

        // when
        Optional<Role> foundRole = roleRepository.findById(role.getId());

        // then
        assertTrue(foundRole.isPresent(), "Role should be found");
        assertEquals(role.getId(), foundRole.get().getId(), "The IDs should match");
    }

    @Test
    void testUpdateRole() {
        // given
        Role role = createTestRole();
        role.setName("ROLE_UPDATED");
        roleRepository.update(role);

        // when
        Optional<Role> updatedRole = roleRepository.findById(role.getId());

        // then
        assertTrue(updatedRole.isPresent(), "Role should be found after update");
        assertEquals("ROLE_UPDATED", updatedRole.get().getName(), "Role name should be updated");
    }

    @Test
    void testFindAllRoles() {
        // given
        createTestRole();
        createTestRole();

        // when
        List<Role> roles = roleRepository.findAll();

        // then
        assertEquals(2, roles.size(), "There should be two roles in the database");
    }

    private Role createTestRole() {
        Role role = new Role();
        role.setName("ROLE_" + UUID.randomUUID());
        roleRepository.create(role);
        return role;
    }

}

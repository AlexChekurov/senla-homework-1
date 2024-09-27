package com.alex.homework4example.controller;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.dto.RoleDTO;
import com.alex.homework4example.handlers.GlobalExceptionHandler;
import com.alex.homework4example.repository.RoleRepository;
import com.alex.homework4example.service.RoleService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig(classes = { AppConfig.class, DataBaseConfig.class, GlobalExceptionHandler.class })
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application-test.properties")
class RoleControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private RoleController roleController;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(roleController)
                .setControllerAdvice(globalExceptionHandler)
                .build();

        roleRepository.deleteAll();
    }

    @SneakyThrows
    @Test
    void createRole() {
        String roleJson = "{\"name\":\"Admin\"}";

        mockMvc.perform(post("/api/v1/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roleJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Admin"));

        assertThat(roleRepository.findAll()).hasSize(1);
        assertThat(roleRepository.findAll().get(0).getName()).isEqualTo("Admin");
    }

    @SneakyThrows
    @Test
    void getAllRoles() {
        roleService.create(new RoleDTO(null, "Admin"));

        mockMvc.perform(get("/api/v1/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Admin"));

        assertThat(roleRepository.findAll()).hasSize(1);
    }

    @SneakyThrows
    @Test
    void getRoleById() {
        RoleDTO createdRole = roleService.create(new RoleDTO(null, "Admin"));

        mockMvc.perform(get("/api/v1/roles/" + createdRole.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Admin"));

        assertThat(roleRepository.findById(createdRole.getId())).isPresent();
    }

    @SneakyThrows
    @Test
    void getRoleById_NotFound() {
        mockMvc.perform(get("/api/v1/roles/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Can't find role with id :999"));

        assertThat(roleRepository.findAll()).isEmpty();
    }

    @SneakyThrows
    @Test
    void updateRole() {
        RoleDTO createdRole = roleService.create(new RoleDTO(null, "Admin"));
        String roleJson = "{\"name\":\"User\"}";

        mockMvc.perform(put("/api/v1/roles/" + createdRole.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roleJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("User"));

        assertThat(roleRepository.findById(createdRole.getId())).isPresent();
        assertThat(roleRepository.findById(createdRole.getId()).get().getName()).isEqualTo("User");
    }

    @SneakyThrows
    @Test
    void updateRole_NotFound() {
        String roleJson = "{\"name\":\"User\"}";

        mockMvc.perform(put("/api/v1/roles/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roleJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Can't update role with id :999"));

        assertThat(roleRepository.findAll()).isEmpty();
    }

    @SneakyThrows
    @Test
    void deleteRole() {
        RoleDTO createdRole = roleService.create(new RoleDTO(null, "Admin"));

        mockMvc.perform(delete("/api/v1/roles/" + createdRole.getId()))
                .andExpect(status().isOk());

        assertThat(roleRepository.findById(createdRole.getId())).isNotPresent();
    }

}
package com.alex.homework4example.controller;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.dto.RoleDTO;
import com.alex.homework4example.dto.UserDTO;
import com.alex.homework4example.entity.Role;
import com.alex.homework4example.entity.User;
import com.alex.homework4example.handlers.GlobalExceptionHandler;
import com.alex.homework4example.repository.RoleRepository;
import com.alex.homework4example.repository.UserRepository;
import com.alex.homework4example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringJUnitConfig(classes = { AppConfig.class, DataBaseConfig.class, GlobalExceptionHandler.class })
@TestPropertySource(locations = "classpath:application-test.properties")
@WebAppConfiguration
@Transactional
class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(globalExceptionHandler)
                .build();

        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @SneakyThrows
    @Test
    void getAllUsers() {
        //given
        Role role = new Role();
        role.setName("Admin");
        roleRepository.create(role);

        User user1 = new User();
        user1.setUsername("alex");
        user1.setPassword("password");
        user1.setRole(role);
        userRepository.create(user1);

        User user2 = new User();
        user2.setUsername("john");
        user2.setPassword("password");
        user2.setRole(role);
        userRepository.create(user2);

        //when, then
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("alex"))
                .andExpect(jsonPath("$[1].username").value("john"));
    }

    @SneakyThrows
    @Test
    void getUserById() {
        //given
        Role role = new Role();
        role.setName("Admin");
        roleRepository.create(role);

        User user = new User();
        user.setUsername("alex");
        user.setPassword("password");
        user.setRole(role);
        userRepository.create(user);

        //when, then
        mockMvc.perform(get("/api/v1/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("alex"));
    }

    @SneakyThrows
    @Test
    void createUser() {
        //given
        Role role = new Role();
        role.setName("Admin");
        roleRepository.create(role);

        //when
        MvcResult result = mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"alex\", \"password\":\"password\", \"role\":{\"id\":" + role.getId() + ", \"name\":\"Admin\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("alex"))
                .andReturn();

        //then
        var userDto = objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class);
        User savedUser = userRepository.findById(userDto.getId()).orElseThrow();
        assertThat(savedUser.getRole().getId()).isEqualTo(role.getId());
    }

    @SneakyThrows
    @Test
    void updateUser() {
        //given
        Role role = new Role();
        role.setName("Admin");
        roleRepository.create(role);

        User user = new User();
        user.setUsername("alex");
        user.setPassword("password");
        user.setRole(role);
        userRepository.create(user);

        Role newRole = new Role();
        newRole.setName("User");
        roleRepository.create(newRole);
        //when
        mockMvc.perform(put("/api/v1/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"alex_updated\", \"password\":\"new_password\", \"role\":{\"id\":" + newRole.getId() + ", \"name\":\"User\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("alex_updated"));

        //then
        User updatedUser = userRepository.findById(user.getId()).orElseThrow();
        assertThat(updatedUser.getUsername()).isEqualTo("alex_updated");
    }

    @SneakyThrows
    @Test
    void deleteUser() {
        //given
        Role role = new Role();
        role.setName("Admin");
        roleRepository.create(role);

        User user = new User();
        user.setUsername("alex");
        user.setPassword("password");
        user.setRole(role);
        userRepository.create(user);

        //when
        mockMvc.perform(delete("/api/v1/users/" + user.getId()))
                .andExpect(status().isOk());

        //then
        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    @SneakyThrows
    @Test
    void getUserById_NotFound() {
        //when, then
        mockMvc.perform(get("/api/v1/users/999"))
                .andExpect(status().isNotFound());
    }

}

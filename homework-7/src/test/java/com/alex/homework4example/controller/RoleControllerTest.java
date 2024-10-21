package com.alex.homework4example.controller;

import com.alex.homework4example.config.AppConfig;
import com.alex.homework4example.config.DataBaseConfig;
import com.alex.homework4example.dto.RoleDTO;
import com.alex.homework4example.handlers.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig(classes = { AppConfig.class, DataBaseConfig.class, GlobalExceptionHandler.class })
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application-test.properties")
class RoleControllerTest {

    private MockMvc mockMvc;
    private String jwtToken;

    @Autowired
    private RoleController roleController;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(roleController)
                .setControllerAdvice(globalExceptionHandler)
                .build();

        // Получаем JWT-токен с ролью ADMIN
        jwtToken = obtainJwtToken("adminUser", "adminPassword");
    }

    @SneakyThrows
    private String obtainJwtToken(String username, String password) {
        String content = "{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }";

        MvcResult result = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getHeader("Authorization");
        assertThat(token).isNotNull();

        return token.replace("Bearer ", "");
    }

    @SneakyThrows
    @Test
    void createRoleTest() {
        // given
        var role = "CREATE_ROLE_TEST";
        var roleId = createRole(role);

        // then
        mockMvc.perform(get("/api/v1/roles/" + roleId)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(role));
    }

    @SneakyThrows
    @Test
    void getAllRoles() {
        // given
        var expectedRoleName = "GET_ALL_ROLES";
        createRole(expectedRoleName);

        // when
        var responseString = mockMvc.perform(get("/api/v1/roles")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        var roles = objectMapper.readValue(responseString, RoleDTO[].class);
        assertThat(roles).hasSizeGreaterThanOrEqualTo(1)
                .extracting(RoleDTO::getName)
                .contains(expectedRoleName);
    }

    @SneakyThrows
    @Test
    void getRoleById() {
        // given
        var role = "GET_ROLE_BY_ID_TEST";
        var roleId = createRole(role);

        // then
        mockMvc.perform(get("/api/v1/roles/" + roleId)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(role));
    }

    @SneakyThrows
    @Test
    void getRoleById_NotFound() {
        // then
        mockMvc.perform(get("/api/v1/roles/999")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Can't find entity with id: 999"));
    }

    @SneakyThrows
    @Test
    void updateRole() {
        // given
        var oldRole = "UPDATE_ROLE_TEST";
        var newRole = "UPDATE_ROLE_TEST_UPDATED";
        var roleString = createRoleWithResponse(oldRole);
        var roleID = JsonPath.read(roleString, "$.id");
        var putRequest = roleString.replace(oldRole, newRole);

        // when
        mockMvc.perform(put("/api/v1/roles/" + roleID)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(putRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newRole));

        // then
        mockMvc.perform(get("/api/v1/roles/" + roleID)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newRole));
    }

    @SneakyThrows
    @Test
    void updateRole_NotFound() {
        // given
        String roleJson = "{\"name\":\"User\"}";

        // when
        mockMvc.perform(put("/api/v1/roles/999")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roleJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Can't update role with id :999"));
    }

    @SneakyThrows
    @Test
    void deleteRole() {
        // given
        var roleId = createRole("DELETE_ROLE_TEST");

        // when
        mockMvc.perform(delete("/api/v1/roles/" + roleId)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());

        // then
        mockMvc.perform(get("/api/v1/roles/" + roleId)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Can't find entity with id: " + roleId));
    }

    @SneakyThrows
    @Test
    void accessWithoutToken_ShouldReturnUnauthorized() {
        mockMvc.perform(get("/api/v1/roles"))
                .andExpect(status().isUnauthorized());
    }

    @SneakyThrows
    @Test
    void accessWithInsufficientRole_ShouldReturnForbidden() {
        // Получаем токен пользователя с ролью USER
        String userToken = obtainJwtToken("testUser", "testPassword");

        mockMvc.perform(get("/api/v1/roles")
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isForbidden());
    }

    @SneakyThrows
    private Integer createRole(String roleName) {
        return JsonPath.read(createRoleWithResponse(roleName), "$.id");
    }

    @SneakyThrows
    private String createRoleWithResponse(String roleName) {
        String roleJson = "{\"name\":\"" + roleName + "\"}";

        return mockMvc.perform(post("/api/v1/roles")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roleJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}

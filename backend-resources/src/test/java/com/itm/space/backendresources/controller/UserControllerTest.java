package com.itm.space.backendresources.controller;


import com.github.javafaker.Faker;
import com.itm.space.backendresources.BaseIntegrationTest;
import com.itm.space.backendresources.api.request.UserRequest;


import com.itm.space.backendresources.service.UserService;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;


import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class UserControllerTest extends BaseIntegrationTest {
    @MockBean
    UserService userService;

    Faker faker = new Faker();


    @Test
    @WithMockUser(roles = "MODERATOR")
    public void createTestWithRolesModerator() throws Exception {
        UserRequest userRequest = new UserRequest(faker.name().username(), faker.internet().emailAddress(), faker.internet().password(), faker.name().firstName(), faker.name().lastName());
        mvc.perform(requestWithContent(post("/api/users"), userRequest))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void createTestWithInvalidUsernameNotBlank() throws Exception {
        UserRequest userRequest = new UserRequest("", faker.internet().emailAddress(), faker.internet().password(), faker.name().firstName(), faker.name().lastName());
        mvc.perform(requestWithContent(post("/api/users"), userRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void createTestWithInvalidUsernameSize() throws Exception {
        UserRequest userRequestSize = new UserRequest("a", faker.internet().emailAddress(), faker.internet().password(), faker.name().firstName(), faker.name().lastName());
        mvc.perform(requestWithContent(post("/api/users"), userRequestSize))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void createTestWithInvalidEmailNotBlank() throws Exception {
        UserRequest userRequest = new UserRequest(faker.name().username(), "", faker.internet().password(), faker.name().firstName(), faker.name().lastName());
        mvc.perform(requestWithContent(post("/api/users"), userRequest))
                .andExpect(status().isBadRequest());
    }


    @Test
    @WithMockUser(roles = "MODERATOR")
    public void createTestWithInvalidEmailInvalid() throws Exception {
        UserRequest userRequest = new UserRequest(faker.name().username(), "invalid_email", faker.internet().password(), faker.name().firstName(), faker.name().lastName());
        mvc.perform(requestWithContent(post("/api/users"), userRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void createTestWithInvalidPasswordNotBlank() throws Exception {
        UserRequest userRequest = new UserRequest(faker.name().username(), faker.internet().emailAddress(), "", faker.name().firstName(), faker.name().lastName());
        mvc.perform(requestWithContent(post("/api/users"), userRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void createTestWithInvalidPasswordSize() throws Exception {
        UserRequest userRequest = new UserRequest(faker.name().username(), faker.internet().emailAddress(), "123", faker.name().firstName(), faker.name().lastName());
        mvc.perform(requestWithContent(post("/api/users"), userRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void createTestWithInvalidFirstNameNotBlank() throws Exception {
        UserRequest userRequest = new UserRequest(faker.name().username(), faker.internet().emailAddress(), faker.internet().password(), "", faker.name().lastName());
        mvc.perform(requestWithContent(post("/api/users"), userRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void createTestWithInvalidLastNameNotBlank() throws Exception {
        UserRequest userRequest = new UserRequest(faker.name().username(), faker.internet().emailAddress(), faker.internet().password(), faker.name().firstName(), "");
        mvc.perform(requestWithContent(post("/api/users"), userRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void createTestWithRolesUser() throws Exception {
        UserRequest userRequest = new UserRequest(faker.name().username(), faker.internet().emailAddress(), faker.internet().password(), faker.name().firstName(), faker.name().lastName());
        mvc.perform(requestWithContent(post("/api/users"), userRequest))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void getUserByIdTestWithRolesModerator() throws Exception {
        UUID id = UUID.fromString("fa4437f6-df9e-4ed6-9b07-1701ccb8a8b6");
        mvc.perform(get("/api/users/" + id))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getUserByIdTestWithRolesUser() throws Exception {
        UUID uuid = UUID.fromString("fa4437f6-df9e-4ed6-9b07-1701ccb8a8b6");
        mvc.perform(get("/api/users/" + uuid))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void helloTestWithRolesModerator() throws Exception {
        mvc.perform(get("/api/users/hello"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void helloTestWithRolesUser() throws Exception {
        mvc.perform(get("/api/users/hello"))
                .andExpect(status().isForbidden());
    }


}
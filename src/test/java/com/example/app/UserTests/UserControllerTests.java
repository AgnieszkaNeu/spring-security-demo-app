package com.example.app.UserTests;

import com.example.app.UserEntity.UserService;
import com.example.app.web.UserController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserService userAuthorityService;

    @Test
    @WithMockUser(roles = {"USER","ADMIN"})
    public void authorities_should_return_Ok() throws Exception {
        Mockito.when(userAuthorityService.getAuthorities()).thenReturn(List.of("ROLE_USER", "ROLE_ADMIN"));

        mockMvc.perform(get("/authorities"))
                .andExpect(jsonPath("$", containsInAnyOrder("ROLE_USER", "ROLE_ADMIN")));
    }

    @Test
    public void authorities_should_return_unauthorized() throws Exception {
        Mockito.when(userAuthorityService.getAuthorities()).thenReturn(List.of("ROLE_USER", "ROLE_ADMIN"));

        mockMvc.perform(get("/authorities"))
                .andExpect(status().isUnauthorized());
    }
}

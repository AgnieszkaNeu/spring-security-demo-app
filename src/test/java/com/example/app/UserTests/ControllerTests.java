package com.example.app.UserTests;

import com.example.app.SecurityOverviewApplication;
import com.example.app.UserEntity.UserAuthorityService;
import com.example.app.config.SecurityConfig;
import com.example.app.web.UserController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {SecurityConfig.class, SecurityOverviewApplication.class, ControllerTests.TestSecurityConfig.class})
public class ControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserAuthorityService userAuthorityService;

    @TestConfiguration
    public static class TestSecurityConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
    @Test
    @WithMockUser(roles = {"ADMIN","USER"})
    public void authorities() throws Exception {
        Mockito.when(userAuthorityService.getAuthorities()).thenReturn(List.of("ROLE_USER", "ROLE_ADMIN"));

        mockMvc.perform(get("/authorities"))
                .andExpect(jsonPath("$", containsInAnyOrder("ROLE_USER", "ROLE_ADMIN")));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void showOnlyForAdmin_should_return_Ok() throws Exception {
        mockMvc.perform(get("/adminPanel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Hello Admin"));
    }

    @Test
    @WithMockUser()
    public void showOnlyForAdmin_should_return_forbidden() throws Exception {
        mockMvc.perform(get("/adminPanel"))
                .andExpect(status().isForbidden());
    }
}

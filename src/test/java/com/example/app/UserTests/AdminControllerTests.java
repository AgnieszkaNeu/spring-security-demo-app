package com.example.app.UserTests;

import com.example.app.SecurityOverviewApplication;
import com.example.app.UserEntity.User;
import com.example.app.UserEntity.UserAuthorityService;
import com.example.app.UserEntity.UserRepository;
import com.example.app.config.SecurityConfig;
import com.example.app.web.AdminController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AdminController.class)
@ContextConfiguration(classes = {SecurityConfig.class, SecurityOverviewApplication.class, AdminControllerTests.TestSecurityConfig.class})
public class AdminControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserRepository userRepository;

    User user_1 = new User(1L,"user_1","password","USER");

    User user_2 = new User(2L,"user_2","password","USER");

    @TestConfiguration
    public static class TestSecurityConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
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

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void showUsers() throws Exception {
        given(userRepository.findAll()).willReturn(List.of(user_1,user_2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].username").value("user_1"))
                .andExpect(jsonPath("$[0].password").value("password"))
                .andExpect(jsonPath("$[0].role").value("USER"))
                .andExpect(jsonPath("$[1].username").value("user_2"))
                .andExpect(jsonPath("$[1].password").value("password"))
                .andExpect(jsonPath("$[1].role").value("USER"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void showUserById() throws Exception {

        given(userRepository.findById(user_1.getId())).willReturn(Optional.of(user_1));

        mockMvc.perform(get("/user/" + user_1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(user_1.getUsername()))
                .andExpect(jsonPath("$.password").value(user_2.getPassword()));

        mockMvc.perform(get("/user/" + 3L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").doesNotExist())
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void changeToRegular() throws Exception {

        given(userRepository.findById(user_1.getId())).willReturn(Optional.of(user_1));

        mockMvc.perform(post("/user/to_regular/" + user_1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("User: user_1 has a role: USER"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void changeToPremium() throws Exception {

        given(userRepository.findById(user_1.getId())).willReturn(Optional.of(user_1));

        mockMvc.perform(post("/user/to_premium/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User: user_1 has a role: PREMIUM_USER"));
    }
}

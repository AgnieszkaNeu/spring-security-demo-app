package com.example.app.UserTests;

import com.example.app.SecurityOverviewApplication;
import com.example.app.UserEntity.UserDto;
import com.example.app.UserEntity.UserService;
import com.example.app.config.SecurityConfig;
import com.example.app.web.AdminController;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@ContextConfiguration(classes = {SecurityConfig.class, SecurityOverviewApplication.class, AdminControllerTests.TestSecurityConfig.class})
public class AdminControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserService userService;

    UserDto userDto_1 = new UserDto("user_1", "USER");
    UserDto userDto_2 = new UserDto("user_2", "USER");

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
        given(userService.showUsers()).willReturn(List.of(userDto_1,userDto_2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].username").value("user_1"))
                .andExpect(jsonPath("$[0].role").value("USER"))
                .andExpect(jsonPath("$[1].username").value("user_2"))
                .andExpect(jsonPath("$[1].role").value("USER"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void showUserById() throws Exception {

        given(userService.showUserById(1L)).willReturn(userDto_1);
        given(userService.showUserById(3L)).willReturn(new UserDto(null,"USER"));

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(userDto_1.username()))
                .andExpect(jsonPath("$.role").value(userDto_1.role()));

        mockMvc.perform(get("/user/" + 3L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").doesNotExist());
    }
}

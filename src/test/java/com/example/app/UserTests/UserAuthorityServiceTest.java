package com.example.app.UserTests;

import com.example.app.UserEntity.UserAuthorityService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserAuthorityServiceTest {

    private UserAuthorityService userAuthorityService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private GrantedAuthority authority1;

    @Mock
    private GrantedAuthority authority2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userAuthorityService = new UserAuthorityService();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getAuthorities() {
        when(authority1.getAuthority()).thenReturn("ROLE_USER");
        when(authority2.getAuthority()).thenReturn("ROLE_ADMIN");

        when(authentication.getAuthorities()).thenReturn((Collection) Arrays.asList(authority1, authority2));

        List<String> result = userAuthorityService.getAuthorities();

        assertEquals(2, result.size());
        assertEquals("ROLE_USER", result.get(0));
        assertEquals("ROLE_ADMIN", result.get(1));

        verify(authentication, times(1)).getAuthorities();
    }
}

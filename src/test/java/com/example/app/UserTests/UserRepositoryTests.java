package com.example.app.UserTests;

import com.example.app.UserEntity.User;
import com.example.app.UserEntity.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    @Test
    public void findByUsername(){

        User user = new User();
        user.setUsername("John");
        user.setPassword("password");
        user.setRole("USER");

        userRepository.save(user);

        User foundedUser = userRepository.findByUsername(user.getUsername());

        assertNotNull(foundedUser);
        assertEquals("John", foundedUser.getUsername());
        assertEquals("password", foundedUser.getPassword());
        assertEquals("USER", foundedUser.getRole());
    }
}

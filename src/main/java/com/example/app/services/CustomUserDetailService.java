package com.example.app.services;

import com.example.app.UserEntity.User;
import com.example.app.UserEntity.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    UserRepository userRepository;
    UserService userService;

    public CustomUserDetailService(UserRepository userRepository,UserService userService){
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = Optional.of(userRepository.findByUsername(username)).orElse(new User());
        return userService.customUserDetails(user);
    }
}

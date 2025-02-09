package com.example.app.web;

import com.example.app.UserEntity.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    public UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/authorities")
    public List<String> getAuthorities(){
        return userService.getAuthorities();
    }

    @GetMapping("/")
    public String showString(){
        return "Spring Security Overview";
    }
}

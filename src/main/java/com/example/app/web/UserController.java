package com.example.app.web;

import com.example.app.UserEntity.UserAuthorityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    public UserAuthorityService userService;

    public UserController(UserAuthorityService userService){
        this.userService = userService;
    }

    @GetMapping("/authorities")
    public List<String> getAuthorities(){
        return userService.getAuthorities();
    }

    @GetMapping("/adminPanel")
    public String showOnlyForAdmin(){
        return "Hello Admin";
    }

    @GetMapping("/")
    public String showString(){
        return "Spring Security Overview";
    }
}

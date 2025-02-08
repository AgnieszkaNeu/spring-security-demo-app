package com.example.app.web;

import com.example.app.UserEntity.UserAuthorityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    public UserAuthorityService userAuthorityService;

    public UserController(UserAuthorityService userService){
        this.userAuthorityService = userService;
    }

    @GetMapping("/authorities")
    public List<String> getAuthorities(){
        return userAuthorityService.getAuthorities();
    }

    @GetMapping("/")
    public String showString(){
        return "Spring Security Overview";
    }
}

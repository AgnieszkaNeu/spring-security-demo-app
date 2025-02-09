package com.example.app.web;

import com.example.app.UserEntity.User;
import com.example.app.UserEntity.UserDto;
import com.example.app.UserEntity.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    UserService userService;

    public AdminController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/adminPanel")
    public String showOnlyForAdmin(){
        return "Hello Admin";
    }

    @GetMapping("/users")
    public List<UserDto> showUsers(){
        return userService.showUsers();
    }

    @GetMapping("/user/{id}")
    public UserDto showUserById(@PathVariable Long id){
        return userService.showUserById(id);
    }

    @PostMapping("/user/to_regular/{id}")
    public UserDto changeToRegular(@PathVariable Long id){
        return userService.changeToRegular(id);
    }

    @PostMapping("/user/to_premium/{id}")
    public UserDto changeToPremium(@PathVariable Long id){
        return userService.changeToPremium(id);
    }

    @PostMapping("/user")
    public UserDto createUser(@RequestBody User user){
        return userService.create(user);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}

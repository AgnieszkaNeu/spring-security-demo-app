package com.example.app.web;

import com.example.app.UserEntity.User;
import com.example.app.UserEntity.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class AdminController {

    UserRepository userRepository;

    public AdminController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> showUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/user/{id}")
    public void changeToPremium(@PathVariable Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setRole("PREMIUM_USER");
        }
    }
}

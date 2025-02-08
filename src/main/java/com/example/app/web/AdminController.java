package com.example.app.web;

import com.example.app.UserEntity.User;
import com.example.app.UserEntity.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AdminController {

    UserRepository userRepository;

    public AdminController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/adminPanel")
    public String showOnlyForAdmin(){
        return "Hello Admin";
    }
    @GetMapping("/users")
    public List<User> showUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public User showUserById(@PathVariable Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseGet(User::new);
    }

    @PostMapping("/user/to_regular/{id}")
    public String changeToRegular(@PathVariable Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setRole("USER");
            userRepository.save(user);
            return "User: " + user.getUsername() + " has a role: " + user.getRole();
        }
        return "No user with the specified ID.";
    }

    @PostMapping("/user/to_premium/{id}")
    public String changeToPremium(@PathVariable Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setRole("PREMIUM_USER");
            userRepository.save(user);
            return "User: " + user.getUsername() + " has a role: " + user.getRole();
        }
        return "No user with the specified ID.";
    }
}

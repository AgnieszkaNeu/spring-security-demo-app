package com.example.app.web;

import com.example.app.UserEntity.User;
import com.example.app.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ViewsController {

    UserService userService;

    public ViewsController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user){
        userService.saveUser(user);
        return "redirect:/";
    }
}

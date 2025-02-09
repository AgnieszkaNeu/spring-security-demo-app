package com.example.app.UserEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    UserRepository userRepository;

    UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDto> showUsers(){
        return userRepository.findAll().stream()
                .map(user -> userMapper.userToUserDto(user))
                .collect(Collectors.toList());
    }

    public UserDto showUserById(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        return userMapper.userToUserDto(optionalUser.orElseGet(User::new));
    }

    public UserDto changeToRegular(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setRole("USER");
            userRepository.save(user);
            return userMapper.userToUserDto(user);
        }
        return userMapper.userToUserDto(new User());
    }

    public UserDto changeToPremium(@PathVariable Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setRole("PREMIUM_USER");
            userRepository.save(user);
            return userMapper.userToUserDto(user);
        }
        return userMapper.userToUserDto(new User());
    }

    public UserDto create(User user){
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    public void deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            userRepository.delete(user);
        }
    }

    public List<String> getAuthorities(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}

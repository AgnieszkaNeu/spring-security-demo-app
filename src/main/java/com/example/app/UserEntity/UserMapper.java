package com.example.app.UserEntity;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    UserRepository userRepository;

    public UserMapper(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User userDtoToUser(UserDto userDto){
        return userRepository.findByUsername(userDto.username());
    }

    public UserDto userToUserDto(User user){
        return new UserDto(user.getUsername(),user.getRole());
    }
}

package com.example.fly_away.Mapper;

import com.example.fly_away.DTO.SignUpRequest;
import com.example.fly_away.Entity.User;

public class UserMapper {
    public static User toEntity(SignUpRequest request){
        User user = new User();

        user.setName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return user;
    }
}

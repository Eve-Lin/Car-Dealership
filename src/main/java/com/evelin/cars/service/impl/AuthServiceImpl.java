package com.evelin.cars.service.impl;

import com.evelin.cars.exception.InvalidEntityException;
import com.evelin.cars.model.Role;
import com.evelin.cars.model.User;
import com.evelin.cars.service.AuthService;
import com.evelin.cars.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private UserService userService;

    @Autowired
    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User register(User user) {
       if(user.getRoles().contains(Role.ADMIN)){
           throw new InvalidEntityException("Admins can not self register.");
       }
       return userService.createUser(user);
    }

    @Override
    public User login(String username, String password) {
        User user = userService.getUserByUsername(username);
        if(user.getPassword().equals(password)){    //please, do not use this in prod
            return user;
        }
        return null;
    }
}

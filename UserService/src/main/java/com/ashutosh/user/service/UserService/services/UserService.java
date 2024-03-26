package com.ashutosh.user.service.UserService.services;

import com.ashutosh.user.service.UserService.entities.User;

import java.util.List;

public interface UserService {
    //create user
    User saveUser(User user);

    //get user by id
    User getUserById(String id);

    //get all users
    List<User> getAllUsers();

    //update user
    User updateUser(User user);

    //delete user
    void deleteUser(String id);
}

package com.example.demo.Service;

import com.example.demo.Model.User;
import com.example.demo.Util.MultiResult;

import java.util.List;

public interface UserService {
    MultiResult<User> login(String username, String password);

    String register(User user);

    void changeStatus(int uid, int status);

    User getUserByUid(int uid);

    User getUserByUsername(String username);

    List<User> getAllUsers();

    List<User> getUnVerifyUsers();
}
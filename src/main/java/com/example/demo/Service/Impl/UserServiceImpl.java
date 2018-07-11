package com.example.demo.Service.Impl;

import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import com.example.demo.Util.MultiResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public MultiResult<User> login(String username, String password) {
        return null;
    }

    @Override
    public String register(User user) {
        return null;
    }

    @Override
    public void changeStatus(int uid, int status) {

    }

    @Override
    public User getUserByUid(int uid) {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public List<User> getUnVerifyUsers() {
        return null;
    }
}
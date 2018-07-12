package com.example.demo.Service;

import com.example.demo.Model.*;
import com.example.demo.Util.MultiResult;

import java.util.List;

public interface UserService {
    MultiResult<User> login(String username, String password);

    String register(User user);

    int recommend(int uid, int rid, String reason);

    Recommendation getRecommendationById(int id);

    Recommendation getRecommendationByUid(int uid);

    List<Recommendation> getRecommendations();

    void changeStatus(int uid, int status);

    User getUserByUid(int uid);

    User getUserByUsername(String username);

    UserInfo getUserInfoByUid(int uid);

    void createOrSaveUserInfo(UserInfo userInfo);

    List<User> getAllUsers();

    List<User> getUnVerifyUsers();

    List<Committee> getCommittees();

    List<Industry> getIndustries();

    List<Seminar> getSeminars();
}

package com.example.demo.Service.Impl;

import com.example.demo.Dao.*;
import com.example.demo.Model.*;
import com.example.demo.Service.UserService;
import com.example.demo.Util.MultiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    RecommendationDao recommendationDao;

    @Autowired
    CommitteeDao committeeDao;

    @Autowired
    IndustryDao industryDao;

    @Autowired
    SeminarDao seminarDao;

    @Override
    public MultiResult<User> login(String username, String password) {
        MultiResult<User> multiResult = new MultiResult<>();
        User user = userDao.getUserByUsername(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                if (user.getStatus() == 0) {
                    return new MultiResult<>("您还未通过审核", null);
                } else if (user.getStatus() == 1) {
                    return new MultiResult<>(null, user);
                } else if (user.getStatus() == 2) {
                    return new MultiResult<>("您的申请被拒绝", null);
                } else {
                    return new MultiResult<>("您的状态未知,请联系管理员", null);
                }
            } else {
                return new MultiResult<>("密码错误", null);
            }
        } else {
            return new MultiResult<>("用户名不存在", null);
        }
    }

    @Override
    public String register(User user) {
        User u = userDao.getUserByUsername(user.getUsername());
        if (u != null) {
            return "用户名已存在";
        } else {
            int r = userDao.insert(user);
            if (r <= 0) {
                return "数据库错误";
            } else {
                return null;
            }
        }
    }

    @Override
    public int recommend(int uid, int rid, String reason) {
        Recommendation recommendation = recommendationDao.getRecommendationByUidAndRid(uid, rid);
        if (recommendation != null) {
            return -1;
        } else {
            recommendation = new Recommendation(uid, rid, reason);
            int r = recommendationDao.insert(recommendation);
            if (r < 0)
                return 0;
            else
                return recommendation.getId();
        }
    }

    @Override
    public Recommendation getRecommendationById(int id) {
        return recommendationDao.getRecommendationById(id);
    }

    @Override
    public Recommendation getRecommendationByUid(int uid) {
        return recommendationDao.getRecommendationByUid(uid);
    }

    @Override
    public List<Recommendation> getRecommendations() {
        return recommendationDao.getRecommendations();
    }

    @Override
    public void changeStatus(int uid, int status) {
        userDao.changeStatus(uid, status);
        recommendationDao.updateStatus(uid,status);
    }

    @Override
    public User getUserByUid(int uid) {
        return userDao.getUserByUid(uid);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public UserInfo getUserInfoByUid(int uid) {
        return userInfoDao.getUserInfoByUid(uid);
    }

    @Override
    public void createOrSaveUserInfo(UserInfo userInfo) {
        if (userInfo != null) {
            UserInfo u = userInfoDao.getUserInfoByUid(userInfo.getUid());
            if (u != null) {
                userInfoDao.update(userInfo);
            } else {
                userInfoDao.insert(userInfo);
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public List<User> getUnVerifyUsers() {
        return userDao.getUnVerifyUsers();
    }

    @Override
    public List<Committee> getCommittees() {
        return committeeDao.getAll();
    }

    @Override
    public List<Industry> getIndustries() {
        return industryDao.getAll();
    }

    @Override
    public List<Seminar> getSeminars() {
        return seminarDao.getAll();
    }
}

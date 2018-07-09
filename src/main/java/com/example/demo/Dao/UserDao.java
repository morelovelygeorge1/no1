package com.example.demo.Dao;

import com.example.demo.Model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {
    @Select("Select * From user")
    List<User> getAllUsers();

    @Select("Select * From user Where status=0")
    List<User> getUnVerifyUsers();

    @Select("Select * From user Where uid=#{uid}")
    User getUserByUid(@Param("uid") int uid);

    @Select("Select * From user Where username=#{username}")
    User getUserByUsername(@Param("username") String username);

    @Insert("INSERT INTO user(username,password,role,name,sex,birthday,address,contact,referrer,industryid,committeeid) VALUES (#{username},#{password},#{role},#{name},#{sex},#{birthday},#{address},#{contact},#{referrer},#{industryid},#{committeeid})")
    @Options(useGeneratedKeys = true, keyProperty = "uid")
    int insert(User user);

    @Update("Update user Set status=#{status} Where uid=#{uid}")
    void changeStatus(@Param("uid") int uid, @Param("status") int status);
}
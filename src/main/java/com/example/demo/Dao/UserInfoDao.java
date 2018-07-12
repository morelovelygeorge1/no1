package com.example.demo.Dao;

import com.example.demo.Model.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserInfoDao {
    @Select("Select * From userinfo;")
    List<UserInfo> getAll();

    @Select("Select * From userinfo Where uid=#{uid}")
    UserInfo getUserInfoByUid(@Param("uid") int uid);

    @Update("Update userinfo Set company=#{company},title=#{title},position=#{position},phone=#{phone},email=#{email},wechat=#{wechat} Where uid=#{uid}")
    void update(UserInfo userInfo);

    @Insert("Insert Into userinfo(uid,company,title,position,phone,email,wechat) Values (#{uid},#{company},#{title},#{position},#{phone},#{email},#{wechat});")
    int insert(UserInfo userInfo);
}

package com.example.demo.Dao;

import com.example.demo.Model.Recommendation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RecommendationDao {
    @Select("Select * From recommendation Where status=0;")
    List<Recommendation> getRecommendations();

    @Select("Select * From recommendation Where status=0 And uid=#{uid} Limit 1;")
    Recommendation getRecommendationByUid(@Param("uid") int uid);

    @Select("Select * From recommendation Where id=#{id}")
    Recommendation getRecommendationById(@Param("id") int id);

    @Select("Select * From recommendation Where uid=#{uid} And rid=#{rid}")
    Recommendation getRecommendationByUidAndRid(@Param("uid") int uid, @Param("rid") int rid);

    @Insert("INSERT INTO recommendation(uid,rid,reason) VALUES (#{uid},#{rid},#{reason})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Recommendation recommendation);

    @Update("Update recommendation Set status=#{status} Where id=#{uid}")
    void updateStatus(@Param("uid") int uid, @Param("status") int status);
}

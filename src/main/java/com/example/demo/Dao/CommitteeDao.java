package com.example.demo.Dao;

import com.example.demo.Model.Committee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommitteeDao {
    @Select("Select * From committee;")
    List<Committee> getAll();

    @Insert("INSERT INTO committee(name) VALUES (#{name});")
    @Options(useGeneratedKeys = true, keyProperty = "committeeid")
    int insert(Committee committee);
}
